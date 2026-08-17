package com.sendmystatus.oeventapp.data.storage

import android.content.Context
import android.content.SharedPreferences

class AndroidTokenStorage(
    private val sharedPreferences: SharedPreferences? = null
) : TokenStorage {
    private var fallbackTokens: AuthTokens? = null

    override suspend fun getTokens(): AuthTokens? {
        if (sharedPreferences != null) {
            val access = sharedPreferences.getString(KEY_ACCESS_TOKEN, null)
            val refresh = sharedPreferences.getString(KEY_REFRESH_TOKEN, null)
            return if (access != null && refresh != null) {
                AuthTokens(access, refresh)
            } else {
                null
            }
        }
        return fallbackTokens
    }

    override suspend fun saveTokens(tokens: AuthTokens) {
        if (sharedPreferences != null) {
            sharedPreferences.edit()
                .putString(KEY_ACCESS_TOKEN, tokens.accessToken)
                .putString(KEY_REFRESH_TOKEN, tokens.refreshToken)
                .apply()
        } else {
            fallbackTokens = tokens
        }
    }

    override suspend fun clearTokens() {
        if (sharedPreferences != null) {
            sharedPreferences.edit()
                .remove(KEY_ACCESS_TOKEN)
                .remove(KEY_REFRESH_TOKEN)
                .apply()
        } else {
            fallbackTokens = null
        }
    }

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"

        fun create(context: Context): AndroidTokenStorage {
            val prefs = context.getSharedPreferences("secure_auth_prefs", Context.MODE_PRIVATE)
            return AndroidTokenStorage(prefs)
        }
    }
}

private val defaultStorage by lazy { AndroidTokenStorage() }

actual fun createTokenStorage(): TokenStorage = defaultStorage
