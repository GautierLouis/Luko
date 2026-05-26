package xyz.luko.apicontracts.routing

import io.ktor.resources.Resource
import xyz.luko.apicontracts.dto.CharacterFrequencyLevelDto

@Suppress("unused")
@Resource("/")
class Destination {
    @Resource("admin")
    class Admin(
        val parent: Destination = Destination(),
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
        val parent: Destination = Destination(),
    )

    @Resource("update_fcm")
    class UpdateFcm(
        val parent: Destination = Destination(),
    )

    @Resource("/me")
    class Me(
        val parent: Destination = Destination(),
    )

    @Resource("characters")
    class Characters(
        val parent: Destination = Destination(),
    ) {
        @Resource("{code}")
        class ByName(
            val parent: Characters = Characters(),
            val code: Int,
        ) {
            @Resource("render")
            class Render(
                val parent: ByName,
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

    @Resource("session")
    class Session(
        val parent: Destination = Destination(),
    ) {
        @Resource("new")
        class New(
            val parent: Session = Session(),
            val levels: List<CharacterFrequencyLevelDto>? = null,
            val limit: Int,
        )

        @Resource("replay")
        class Replay(
            val parent: Session = Session(),
            val seed: Long,
        )
    }

}
