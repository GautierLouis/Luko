package com.louisgautier.network

import io.ktor.http.URLProtocol

internal sealed class NetworkEnvironment(
    val scheme: URLProtocol,
    val host: String,
) {
    data object Dev : NetworkEnvironment(
        scheme = URLProtocol.HTTP,
        host = "10.0.2.2",
    )

    data object Preprod :
        NetworkEnvironment(
            scheme = URLProtocol.HTTPS,
            host = "learn-chinese-staging.up.railway.app",
        )

    data object Prod : NetworkEnvironment(
        scheme = URLProtocol.HTTPS,
        host = "learn-chinese-production.up.railway.app",
    )
}