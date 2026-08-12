package xyz.luko.server.router.route

import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import xyz.luko.apicontracts.dto.StrokeDto
import xyz.luko.server.router.RouteController

class UnauthedRouteController : RouteController {
    override fun Route.register() {
        get("/") {
            call.respondText("Ktor")
//            call.respondText(buildComparisonHtml(globalReference, globalInput), ContentType.Text.Html)
        }
    }
}


var globalReference: List<List<List<Float>>> = emptyList()
var globalInput: List<StrokeDto> = emptyList()

private fun buildComparisonHtml(
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
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="utf-8">
            <title>Stroke Comparison</title>
            <style>
                body { background: #1a1a1a; color: #eee; font-family: monospace; padding: 20px; }
                .legend { margin-bottom: 12px; }
                .legend span { margin-right: 24px; }
                .ref-dot { color: #4A90D9; }
                .input-dot { color: #E85D4C; }
                svg { background: #fff; border: 1px solid #444; }
            </style>
        </head>
        <body>
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
        </body>
        </html>
    """.trimIndent()
}
