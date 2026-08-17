package com.sendmystatus.oeventapp.data.storage

import java.util.prefs.Preferences

class JvmTokenStorage(
    private val preferences: Preferences = Preferences.userNodeForPackage(JvmTokenStorage::class.java)
) : TokenStorage {

    override suspend fun getTokens(): AuthTokens? {
        val access = preferences.get(KEY_ACCESS_TOKEN, null)
        val refresh = preferences.get(KEY_REFRESH_TOKEN, null)
        return if (access != null && refresh != null) {
            AuthTokens(access, refresh)
        } else {
            null
        }
    }

    override suspend fun saveTokens(tokens: AuthTokens) {
        preferences.put(KEY_ACCESS_TOKEN, tokens.accessToken)
        preferences.put(KEY_REFRESH_TOKEN, tokens.refreshToken)
    }

    override suspend fun clearTokens() {
        preferences.remove(KEY_ACCESS_TOKEN)
        preferences.remove(KEY_REFRESH_TOKEN)
    }

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
    }
}

private val defaultStorage by lazy { JvmTokenStorage() }

actual fun createTokenStorage(): TokenStorage = defaultStorage
