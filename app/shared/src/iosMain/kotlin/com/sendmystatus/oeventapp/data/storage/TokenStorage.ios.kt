package com.sendmystatus.oeventapp.data.storage

import platform.Foundation.NSUserDefaults

class IosTokenStorage(
    private val userDefaults: NSUserDefaults = NSUserDefaults.standardUserDefaults
) : TokenStorage {

    override suspend fun getTokens(): AuthTokens? {
        val access = userDefaults.stringForKey(KEY_ACCESS_TOKEN)
        val refresh = userDefaults.stringForKey(KEY_REFRESH_TOKEN)
        return if (access != null && refresh != null) {
            AuthTokens(access, refresh)
        } else {
            null
        }
    }

    override suspend fun saveTokens(tokens: AuthTokens) {
        userDefaults.setObject(tokens.accessToken, KEY_ACCESS_TOKEN)
        userDefaults.setObject(tokens.refreshToken, KEY_REFRESH_TOKEN)
    }

    override suspend fun clearTokens() {
        userDefaults.removeObjectForKey(KEY_ACCESS_TOKEN)
        userDefaults.removeObjectForKey(KEY_REFRESH_TOKEN)
    }

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
    }
}

private val defaultStorage by lazy { IosTokenStorage() }

actual fun createTokenStorage(): TokenStorage = defaultStorage
