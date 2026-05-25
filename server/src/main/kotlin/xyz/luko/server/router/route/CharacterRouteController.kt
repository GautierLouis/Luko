package xyz.luko.server.router.route

import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.authenticate
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import xyz.luko.apicontracts.dto.StrokeDto
import xyz.luko.apicontracts.routing.Destination
import xyz.luko.server.domain.repo.DictionaryRepository
import xyz.luko.server.domain.repo.SessionRepository
import xyz.luko.server.error.dictionaryNotFound
import xyz.luko.server.error.graphicNotFound
import xyz.luko.server.plugin.BEARER
import xyz.luko.server.router.RouteController

class CharacterRouteController(
    private val sessionRepository: SessionRepository,
    private val dictionaryRepository: DictionaryRepository,
) : RouteController {

    private suspend inline fun <reified T : Any> ApplicationCall.respondOk(body: T) =
        this.respond(status = HttpStatusCode.OK, message = body)

    override fun Route.register() {
        authenticate(BEARER) {
            get<Destination.GenerateSession> { resources ->
                sessionRepository.generateSession(resources).let {
                    call.respondOk(it)
                }
            }

            get<Destination.Characters.ByLevel> { resources ->
                dictionaryRepository.getByLevel(resources).let {
                    call.respondOk(it)
                }
            }

            get<Destination.Characters.Search> { resources ->
                dictionaryRepository.search(resources).let {
                    call.respondOk(it)
                }
            }

            get<Destination.Characters.ByName> { resource ->
                dictionaryRepository.get(resource)?.let {
                    call.respondOk(it)
                } ?: throw dictionaryNotFound(resource.code)
            }

            get<Destination.Characters.ByName.Render> { resource ->
                val graphic = dictionaryRepository.get(resource.parent)
                    ?: throw graphicNotFound(resource.parent.code)

                val colors = listOf(
                    "#378ADD", "#1D9E75", "#D85A30", "#D4537E", "#7F77DD",
                    "#639922", "#BA7517", "#E24B4A", "#EF9F27", "#AFA9EC", "#5DCAA5"
                )

                fun List<String>.toPaths() = mapIndexed { i, path ->
                    val color = colors[i % colors.size]
                    """<path d="$path" fill="none" stroke="$color" stroke-width="8" stroke-linecap="round"/>"""
                }.joinToString("\n        ")

                fun List<StrokeDto>.toSmoothPaths() = mapIndexed { i, path ->
                    val color = colors[i % colors.size]
                    """<path d="$path" fill="none" stroke="$color" stroke-width="8" stroke-linecap="round"/>"""
                }.joinToString("\n        ")

                val html = """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8"/>
            <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
            <title>&#${graphic.code}; — Graphic</title>
            <style>
                * { box-sizing: border-box; margin: 0; padding: 0; }
                body { font-family: sans-serif; background: #111; color: #eee; display: flex; flex-direction: column; align-items: center; padding: 2rem; gap: 1.5rem; }
                h1 { font-size: 3rem; }
                .controls { display: flex; gap: 0.75rem; }
                button { padding: 0.5rem 1.25rem; border-radius: 8px; border: 1px solid #444; background: #222; color: #eee; cursor: pointer; font-size: 0.9rem; transition: background 0.2s; }
                button:hover { background: #333; }
                button.active { background: #378ADD; border-color: #378ADD; color: #fff; }
                svg { border: 1px solid #333; border-radius: 8px; background: #1a1a1a; width: 100%; max-width: 600px; height: auto; }
            </style>
        </head>
        <body>
            <h1>&#${graphic.code};</h1>
            <div class="controls">
                <button class="active" onclick="show('original')">Original</button>
                <button onclick="show('smoothed')">Medians</button>
            </div>
            <svg viewBox="-30 -80 1100 1000" xmlns="http://www.w3.org/2000/svg">
                <g transform="translate(0, 920) scale(1, -1)">
                    <g id="original">
                        ${graphic.strokes.toPaths()}
                    </g>
                    <g id="smoothed" style="display:none">
                        ${graphic.medians.toSmoothPaths()}
                    </g>
                </g>
            </svg>
            <script>
                const layers = ['original', 'medians', 'smoothed'];
                function show(id) {
                    layers.forEach(l => {
                        document.getElementById(l).style.display = l === id ? '' : 'none';
                    });
                    document.querySelectorAll('button').forEach((b, i) => {
                        b.classList.toggle('active', layers[i] === id);
                    });
                }
            </script>
        </body>
        </html>
    """.trimIndent()

                call.respondText(html, ContentType.Text.Html)
            }
        }
    }
}
