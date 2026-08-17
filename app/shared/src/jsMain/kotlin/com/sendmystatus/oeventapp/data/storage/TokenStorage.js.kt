package com.sendmystatus.oeventapp.data.storage

import kotlinx.browser.localStorage

class JsTokenStorage : TokenStorage {
    override suspend fun getTokens(): AuthTokens? {
        val access = localStorage.getItem(KEY_ACCESS_TOKEN)
        val refresh = localStorage.getItem(KEY_REFRESH_TOKEN)
        return if (access != null && refresh != null) {
            AuthTokens(access, refresh)
        } else {
            null
        }
    }

    override suspend fun saveTokens(tokens: AuthTokens) {
        localStorage.setItem(KEY_ACCESS_TOKEN, tokens.accessToken)
        localStorage.setItem(KEY_REFRESH_TOKEN, tokens.refreshToken)
    }

    override suspend fun clearTokens() {
        localStorage.removeItem(KEY_ACCESS_TOKEN)
        localStorage.removeItem(KEY_REFRESH_TOKEN)
    }

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
    }
}

private val defaultStorage by lazy { JsTokenStorage() }

actual fun createTokenStorage(): TokenStorage = defaultStorage
