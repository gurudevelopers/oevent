package com.sendmystatus.oeventapp

import com.sendmystatus.oeventapp.data.storage.AuthTokens
import com.sendmystatus.oeventapp.data.storage.InMemoryTokenStorage
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class TokenStorageTest {

    @Test
    fun testInMemoryTokenStorageSaveAndRetrieve() = runTest {
        val storage = InMemoryTokenStorage()
        assertNull(storage.getTokens())

        val tokens = AuthTokens(accessToken = "access_123", refreshToken = "refresh_456")
        storage.saveTokens(tokens)

        val retrieved = storage.getTokens()
        assertEquals("access_123", retrieved?.accessToken)
        assertEquals("refresh_456", retrieved?.refreshToken)

        storage.clearTokens()
        assertNull(storage.getTokens())
    }
}
