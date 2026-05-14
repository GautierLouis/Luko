package xyz.luko.server.router

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import xyz.luko.server.router.route.AdminRouteController
import xyz.luko.server.router.route.AuthedRouteController
import xyz.luko.server.router.route.CharacterRouteController
import xyz.luko.server.router.route.UnauthedRouteController

val routerModule = module {
    singleOf(::AdminRouteController) bind RouteController::class
    singleOf(::UnauthedRouteController) bind RouteController::class
    singleOf(::AuthedRouteController) bind RouteController::class
    singleOf(::CharacterRouteController) bind RouteController::class
}