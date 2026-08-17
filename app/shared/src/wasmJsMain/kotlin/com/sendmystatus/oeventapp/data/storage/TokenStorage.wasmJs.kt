package com.sendmystatus.oeventapp.data.storage

class WasmJsTokenStorage : TokenStorage {
    private var memoryTokens: AuthTokens? = null

    override suspend fun getTokens(): AuthTokens? = memoryTokens

    override suspend fun saveTokens(tokens: AuthTokens) {
        memoryTokens = tokens
    }

    override suspend fun clearTokens() {
        memoryTokens = null
    }
}

private val defaultStorage by lazy { WasmJsTokenStorage() }

actual fun createTokenStorage(): TokenStorage = defaultStorage
