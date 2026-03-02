package com.louisgautier.apicontracts.routing

import com.louisgautier.apicontracts.dto.CharacterFrequencyLevelDto
import io.ktor.resources.Resource

@Resource("/")
class EndPoint {
    @Resource("admin")
    class Admin(
        val parent: EndPoint = EndPoint(),
    ) {
        @Resource("swagger")
        class Swagger(
            val parent: Admin = Admin(),
        )

        @Resource("openapi")
        class OpenAPI(
            val parent: Admin = Admin(),
        )

        @Resource("metrics")
        class Metrics(
            val parent: Admin = Admin(),
        )
    }

    @Resource("register")
    class Register(
        val parent: EndPoint = EndPoint(),
    )

    @Resource("register_anon")
    class RegisterAnonymously(
        val parent: EndPoint = EndPoint(),
    )

    @Resource("login")
    class Login(
        val parent: EndPoint = EndPoint(),
    )

    @Resource("/me")
    class Me(
        val parent: EndPoint = EndPoint(),
    )

    @Resource("/refresh_token")
    class RefreshToken(
        val parent: EndPoint = EndPoint(),
    )

    @Resource("characters")
    class Characters(
        val parent: EndPoint = EndPoint(),
    ) {
        @Resource("{code}")
        class ByName(
            val parent: Characters = Characters(),
            val code: Int,
        ) {
            @Resource("svg")
            class SVG(
                val parent: ByName
            )
        }

        @Resource("by_level")
        class ByLevel(
            val parent: Characters = Characters(),
            val level: CharacterFrequencyLevelDto,
            val page: Int? = null,
            val limit: Int? = null,
        )
    }

    @Resource("level")
    class Level(
        val parent: EndPoint = EndPoint(),
    )

    @Resource("generateQuiz")
    class GenerateSession(
        val parent: EndPoint = EndPoint(),
        val level: List<CharacterFrequencyLevelDto> = CharacterFrequencyLevelDto.entries,
        val limit: Int
    )
}
