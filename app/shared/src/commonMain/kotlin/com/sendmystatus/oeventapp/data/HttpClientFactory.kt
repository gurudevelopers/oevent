package com.sendmystatus.oeventapp.data

import com.sendmystatus.oeventapp.data.model.AuthResponse
import com.sendmystatus.oeventapp.data.model.RefreshTokenRequest
import com.sendmystatus.oeventapp.data.storage.AuthTokens
import com.sendmystatus.oeventapp.data.storage.TokenStorage
import com.sendmystatus.oeventapp.data.storage.createTokenStorage
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.serialization.json.Json

expect fun createHttpClientEngine(): HttpClientEngine

class HttpClientFactory(
    val tokenStorage: TokenStorage = createTokenStorage()
) {
    private val _onLogoutRequired = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val onLogoutRequired = _onLogoutRequired.asSharedFlow()

    fun create(): HttpClient {
        return HttpClient(createHttpClientEngine()) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
            install(Logging) {
                level = LogLevel.ALL
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        val tokens = tokenStorage.getTokens()
                        tokens?.let {
                            BearerTokens(
                                accessToken = it.accessToken,
                                refreshToken = it.refreshToken
                            )
                        }
                    }

                    refreshTokens {
                        val currentRefreshToken = oldTokens?.refreshToken
                            ?: tokenStorage.getTokens()?.refreshToken

                        if (currentRefreshToken.isNullOrBlank()) {
                            _onLogoutRequired.tryEmit(Unit)
                            return@refreshTokens null
                        }

                        try {
                            val response = client.post("https://oevent.com/refresh-token") {
                                contentType(ContentType.Application.Json)
                                setBody(RefreshTokenRequest(refreshToken = currentRefreshToken))
                                markAsRefreshTokenRequest()
                            }.body<AuthResponse>()

                            val newTokens = AuthTokens(
                                accessToken = response.token,
                                refreshToken = response.refreshToken ?: currentRefreshToken
                            )

                            tokenStorage.saveTokens(newTokens)

                            BearerTokens(
                                accessToken = newTokens.accessToken,
                                refreshToken = newTokens.refreshToken
                            )
                        } catch (e: Exception) {
                            tokenStorage.clearTokens()
                            _onLogoutRequired.tryEmit(Unit)
                            null
                        }
                    }

                    sendWithoutRequest { request ->
                        request.url.host.contains("oevent.com") &&
                            !request.url.encodedPath.contains("/login") &&
                            !request.url.encodedPath.contains("/otp") &&
                            !request.url.encodedPath.contains("/registration") &&
                            !request.url.encodedPath.contains("/refresh-token")
                    }
                }
            }
        }
    }
}
