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

    @Resource("register_anon")
    class RegisterAnonymously(
        val parent: EndPoint = EndPoint(),
    )

    @Resource("update_fcm")
    class UpdateFcm(
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
            @Resource("graphic")
            class Graphic(
                val parent: ByName
            )
        }

        @Resource("by_level")
        class ByLevel(
            val parent: Characters = Characters(),
            val level: CharacterFrequencyLevelDto? = null,
            val page: Int = 0,
            val limit: Int = 100,
        )

        @Resource("search")
        class Search(
            val parent: Characters = Characters(),
            val levels: List<CharacterFrequencyLevelDto>? = null,
            val query: String = "",
            val page: Int = 0,
            val limit: Int = 100,
        )
    }

    @Resource("level")
    class Level(
        val parent: EndPoint = EndPoint(),
    )

    @Resource("createSession")
    class GenerateSession(
        val parent: EndPoint = EndPoint(),
        val levels: List<CharacterFrequencyLevelDto>? = null,
        val limit: Int
    )
}
