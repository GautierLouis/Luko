package com.louisgautier.network

import com.louisgautier.network.NamedNetworkModule.AUTHED_CLIENT
import com.louisgautier.network.NamedNetworkModule.UNAUTHED_CLIENT
import com.louisgautier.network.interfaces.AuthService
import com.louisgautier.network.interfaces.CharacterService
import com.louisgautier.network.interfaces.UserService
import com.louisgautier.utils.AppConfig
import io.ktor.http.URLProtocol
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

private object NamedNetworkModule {
    const val UNAUTHED_CLIENT = "UNAUTHED_CLIENT"
    const val AUTHED_CLIENT = "AUTHED_CLIENT"
}

sealed class NetworkEnvironment(
    val scheme: URLProtocol,
    val host: String,
    val port: Int
) {
    object Dev : NetworkEnvironment(URLProtocol.HTTP, "10.0.2.2", 8080)
    object Preprod : NetworkEnvironment(URLProtocol.HTTPS, "preprod.api.example.com", 8443)
    object Prod : NetworkEnvironment(URLProtocol.HTTPS, "api.example.com", 443)
}

val networkModule: Module = module {
    single {
        when (get<AppConfig>().flavor) {
            "dev" -> NetworkEnvironment.Dev
            "preprod" -> NetworkEnvironment.Preprod
            else -> NetworkEnvironment.Prod
        }
    }

    single {
        DefaultService(
            tokenAccessor = get(),
            env = get(),
            appConfig = get()
        )
    }
    single(named(UNAUTHED_CLIENT)) { get<DefaultService>().unauthedClient }
    single(named(AUTHED_CLIENT)) { get<DefaultService>().authedClient }

    single { DefaultAuthService(get(named(UNAUTHED_CLIENT))) } bind AuthService::class
    single { DefaultUserService(get(named(AUTHED_CLIENT))) } bind UserService::class
    single { DefaultCharacterService(get(named(UNAUTHED_CLIENT))) } bind CharacterService::class
}
