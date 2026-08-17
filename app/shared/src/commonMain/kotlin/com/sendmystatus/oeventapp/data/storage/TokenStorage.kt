package com.sendmystatus.oeventapp.data.storage

data class AuthTokens(
    val accessToken: String,
    val refreshToken: String
)

interface TokenStorage {
    suspend fun getTokens(): AuthTokens?
    suspend fun saveTokens(tokens: AuthTokens)
    suspend fun clearTokens()
}

class InMemoryTokenStorage(
    private var tokens: AuthTokens? = null
) : TokenStorage {
    override suspend fun getTokens(): AuthTokens? = tokens

    override suspend fun saveTokens(tokens: AuthTokens) {
        this.tokens = tokens
    }

    override suspend fun clearTokens() {
        this.tokens = null
    }
}

expect fun createTokenStorage(): TokenStorage
