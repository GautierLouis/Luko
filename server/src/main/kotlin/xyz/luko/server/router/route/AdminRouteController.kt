package xyz.luko.server.router.route

import io.ktor.http.ContentType
import io.ktor.openapi.OpenApiInfo
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.resources.get
import io.ktor.server.resources.href
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.openapi.OpenApiDocSource
import io.ktor.server.routing.routingRoot
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.andWhere
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import xyz.luko.apicontracts.dto.StrokeDto
import xyz.luko.apicontracts.routing.Destination
import xyz.luko.server.data.database.table.DictionaryTable
import xyz.luko.server.data.database.table.GraphicTable
import xyz.luko.server.data.database.table.SessionResponseTable
import xyz.luko.server.domain.model.SessionResponseRow
import xyz.luko.server.domain.repo.toSessionResponseRow
import xyz.luko.server.router.RouteController

class AdminRouteController(
    private val metricsRegistry: PrometheusMeterRegistry
) : RouteController {

    override fun Route.register() {
        swaggerUI(path = "admin/swagger") {
            info = OpenApiInfo("Luko API", "1.0")
            source = OpenApiDocSource.Routing(ContentType.Application.Json) {
                routingRoot.descendants()
            }
        }

        get<Destination.Admin.Metrics> {
            call.respond(metricsRegistry.scrape())
        }

        // EndPoint for debug only. I'm fine of ugly code that break Clean Archi.
        get<Destination.Admin.StrokeComparison> { params ->
            val availableCodes: List<Int> = params.sessionId?.let { sessionId ->
                suspendTransaction {
                    SessionResponseTable.select(SessionResponseTable.code)
                        .where { SessionResponseTable.sessionId eq sessionId }
                        .toList()
                        .map { it[SessionResponseTable.code] }
                }

            } ?: emptyList()

            val sessionResponse: SessionResponseRow? =
                if (params.sessionId != null && params.code != null) {
                    suspendTransaction {
                        SessionResponseTable.selectAll()
                            .where { SessionResponseTable.sessionId eq params.sessionId }
                            .andWhere { SessionResponseTable.code eq params.code!! }
                            .firstOrNull()
                            ?.toSessionResponseRow()
                    }
                } else null

            val reference: List<List<List<Float>>>? =
                if (params.code != null && params.referenceType != null) {
                    suspendTransaction {
                        when (params.referenceType) {
                            "raw" -> GraphicTable
                                .select(GraphicTable.medians)
                                .where { GraphicTable.code eq params.code!! }
                                .firstOrNull()
                                ?.let { Json.decodeFromString(it[GraphicTable.medians]) }


                            "smoothed" -> DictionaryTable.select(DictionaryTable.medians)
                                .where { DictionaryTable.code eq params.code!! }
                                .firstOrNull()
                                ?.let {
                                    val strokes =
                                        Json.decodeFromString<List<StrokeDto>>(it[DictionaryTable.medians])
                                    strokes.map { stroke ->
                                        stroke.points.map { point ->
                                            listOf(point.x, point.y)
                                        }
                                    }
                                }

                            else -> null
                        }
                    }
                } else null

            call.respondText(
                buildComparisonPageHtml(
                    baseHref = call.application.href(Destination.Admin.StrokeComparison()),
                    params = params,
                    availableCodes = availableCodes,
                    sessionResponse = sessionResponse,
                    reference = reference,
                ),
                ContentType.Text.Html,
            )
        }
    }
}

private fun buildComparisonPageHtml(
    baseHref: String,
    params: Destination.Admin.StrokeComparison,
    availableCodes: List<Int>,
    sessionResponse: SessionResponseRow?,
    reference: List<List<List<Float>>>?,
): String {
    // Each control submits a GET to the same resource with updated params — plain HTML, no JS needed.
    // sessionId step: text input, submits with just sessionId set
    val sessionIdForm = """
        <form method="get" action="$baseHref">
            <label>Session ID
                <input type="text" name="sessionId" value="${params.sessionId ?: ""}" required />
            </label>
            <button type="submit">Load session</button>
        </form>
    """.trimIndent()

    // code step: only shown once sessionId is set and codes are available
    val codeForm = if (params.sessionId != null) """
        <form method="get" action="$baseHref">
            <input type="hidden" name="sessionId" value="${params.sessionId}" />
            <label>Character Code
                <select name="code" required>
                    <option value="">-- select --</option>
                    ${
        availableCodes.joinToString("\n") { c ->
            val selected = if (c == params.code) "selected" else ""
            """<option value="$c" $selected>$c</option>"""
        }
    }
                </select>
            </label>
            <label>Reference Type
                <select name="referenceType" required>
                    <option value="raw" ${if (params.referenceType == "raw") "selected" else ""}>Raw medians</option>
                    <option value="smoothed" ${if (params.referenceType == "smoothed") "selected" else ""}>Smoothed path</option>
                </select>
            </label>
            <button type="submit">Compare</button>
        </form>
    """.trimIndent() else ""

    val resultHtml = if (sessionResponse != null && reference != null) {
        val input: List<StrokeDto> = Json.decodeFromString(sessionResponse.strokes)
        buildComparisonHtmlFragment(
            reference,
            input
        ) // see below — factored out of buildComparisonHtml
    } else ""

    return """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="utf-8">
            <title>Stroke Comparison — Dev Tool</title>
            <style>
                body { background: #1a1a1a; color: #eee; font-family: monospace; padding: 20px; }
                label { display: block; margin-top: 12px; }
                input, select { padding: 6px; font-family: monospace; margin-top: 4px; width: 240px; }
                button { margin-top: 16px; padding: 8px 16px; font-family: monospace; cursor: pointer; }
                form { margin-bottom: 20px; }
            </style>
        </head>
        <body>
            <h2>Stroke Comparison</h2>
            $sessionIdForm
            $codeForm
            $resultHtml
        </body>
        </html>
    """.trimIndent()
}

private fun buildComparisonHtmlFragment(
    reference: List<List<List<Float>>>,
    input: List<StrokeDto>,
): String {
    // Display flip: glyph-space is Y-up (900 at top per makemeahanzi's convention),
    // SVG is Y-down — flip so the character renders right-side-up on screen.
    val displayOriginY = 900f

    val refPolylines = reference.mapIndexed { i, stroke ->
        val points = stroke.joinToString(" ") { (x, y) -> "${x},${displayOriginY - y}" }
        """<polyline points="$points" fill="none" stroke="#4A90D9" stroke-width="4" opacity="0.7" />
           <text x="${stroke.first()[0]}" y="${displayOriginY - stroke.first()[1]}" fill="#4A90D9" font-size="20">R$i</text>"""
    }.joinToString("\n")

    val inputPolylines = input.mapIndexed { i, stroke ->
        val points = stroke.points.joinToString(" ") { p -> "${p.x},${displayOriginY - p.y}" }
        val first = stroke.points.first()
        """<polyline points="$points" fill="none" stroke="#E85D4C" stroke-width="4" opacity="0.7" stroke-dasharray="6,4" />
           <text x="${first.x}" y="${displayOriginY - first.y}" fill="#E85D4C" font-size="20" dy="20">U$i</text>"""
    }.joinToString("\n")

    return """
        <div class="legend">
            <span class="ref-dot">● Reference (${reference.size} strokes)</span>
            <span class="input-dot">● User input (${input.size} strokes)</span>
        </div>
        <svg width="1024" height="1024" viewBox="-150 -200 1200 1200">
            <line x1="-150" y1="0" x2="1050" y2="0" stroke="#ddd" stroke-width="1" />
            <line x1="0" y1="-200" x2="0" y2="1000" stroke="#ddd" stroke-width="1" />
            $refPolylines
            $inputPolylines
        </svg>
    """.trimIndent()
}
