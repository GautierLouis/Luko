package com.louisgautier.server.router

import com.louisgautier.server.router.route.AdminRouteController
import com.louisgautier.server.router.route.AuthedRouteController
import com.louisgautier.server.router.route.CharacterRouteController
import com.louisgautier.server.router.route.UnauthedRouteController
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val routerModule = module {
    singleOf(::AdminRouteController) bind RouteController::class
    singleOf(::UnauthedRouteController) bind RouteController::class
    singleOf(::AuthedRouteController) bind RouteController::class
    singleOf(::CharacterRouteController) bind RouteController::class
}