package com.sendmystatus.oeventapp.di

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.engine.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.Serializable
import kotlinx.coroutines.flow.MutableSharedFlow
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import com.sendmystatus.oeventapp.network.NetworkConfig
import com.sendmystatus.oeventapp.data.storage.TokenStorage
import com.sendmystatus.oeventapp.data.storage.AuthTokens
import com.sendmystatus.oeventapp.data.model.AuthResponse
import com.sendmystatus.oeventapp.data.model.RefreshTokenRequest

@Serializable
data class GraphQLRequest(
    val query: String,
    val variables: Map<String, JsonElement>? = null,
    val operationName: String? = null
)

@Module
class NetworkModule {

    @Single
    fun provideNetworkConfig(): NetworkConfig = NetworkConfig(
        baseUrl = "https://api.oeventapp.com",
        wsUrl = "wss://api.oeventapp.com/ws"
    )

    @Single
    fun provideHttpClient(
        config: NetworkConfig,
        json: Json,
        tokenStorage: TokenStorage,
        logoutFlow: MutableSharedFlow<Unit>
    ): HttpClient = com.sendmystatus.oeventapp.di.provideHttpClient(
        config = config,
        json = json,
        tokenStorage = tokenStorage,
        logoutFlow = logoutFlow
    )
}

fun provideHttpClient(
    config: NetworkConfig,
    json: Json,
    tokenStorage: TokenStorage,
    logoutFlow: MutableSharedFlow<Unit>
): HttpClient = HttpClient {
    engine {
        configureEngine(this, config)
    }

    install(ContentNegotiation) {
        json(json)
    }
    
    install(Logging) {
        level = if (config.isDebug) LogLevel.ALL else LogLevel.INFO
        logger = Logger.DEFAULT
    }

    install(Auth) {
        bearer {
            loadTokens {
                tokenStorage.getTokens()?.let {
                    BearerTokens(it.accessToken, it.refreshToken)
                }
            }

            refreshTokens {
                val currentRefreshToken = oldTokens?.refreshToken
                    ?: tokenStorage.getTokens()?.refreshToken

                if (currentRefreshToken.isNullOrBlank()) {
                    logoutFlow.tryEmit(Unit)
                    return@refreshTokens null
                }

                try {
                    val response = client.post("refresh-token") {
                        contentType(ContentType.Application.Json)
                        setBody(RefreshTokenRequest(refreshToken = currentRefreshToken))
                        markAsRefreshTokenRequest()
                    }.body<AuthResponse>()

                    val newTokens = AuthTokens(
                        accessToken = response.token,
                        refreshToken = response.refreshToken ?: currentRefreshToken
                    )

                    tokenStorage.saveTokens(newTokens)
                    BearerTokens(newTokens.accessToken, newTokens.refreshToken)
                } catch (e: Exception) {
                    tokenStorage.clearTokens()
                    logoutFlow.tryEmit(Unit)
                    null
                }
            }

            sendWithoutRequest { request ->
                val path = request.url.encodedPath
                !path.contains("/login") && 
                !path.contains("/otp") && 
                !path.contains("/registration") && 
                !path.contains("/refresh-token")
            }
        }
    }
    
    install(WebSockets)
    
    install(HttpTimeout) {
        requestTimeoutMillis = config.timeoutMillis
        connectTimeoutMillis = config.timeoutMillis
        socketTimeoutMillis = config.timeoutMillis
    }

    install(HttpRequestRetry) {
        retryOnExceptionOrServerErrors(maxRetries = config.retryCount)
        exponentialDelay()
    }

    install(HttpCache)
    
    defaultRequest {
        url(config.baseUrl)
        contentType(ContentType.Application.Json)
    }
}

expect fun HttpClientConfig<*>.configureEngine(engineConfig: HttpClientEngineConfig, config: NetworkConfig)
