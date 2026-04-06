package com.louisgautier.network

import io.ktor.http.URLProtocol

internal sealed class NetworkEnvironment(
    val scheme: URLProtocol,
    val host: String,
    val port: Int
) {
    data object Dev : NetworkEnvironment(URLProtocol.Companion.HTTP, "10.0.2.2", 8080)
    data object Preprod :
        NetworkEnvironment(URLProtocol.Companion.HTTPS, "preprod.api.example.com", 8443)

    data object Prod : NetworkEnvironment(URLProtocol.Companion.HTTPS, "api.example.com", 443)
}