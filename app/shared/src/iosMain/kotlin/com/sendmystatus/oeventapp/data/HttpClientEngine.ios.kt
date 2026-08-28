package com.sendmystatus.oeventapp.data

import io.ktor.client.engine.*
import io.ktor.client.engine.darwin.*
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.convert
import platform.Foundation.*
import platform.Security.*

@OptIn(ExperimentalForeignApi::class)
actual fun createHttpClientEngine(): HttpClientEngine {
    return Darwin.create {
        handleChallenge { _, _, challenge, completionHandler ->
            val serverTrust = challenge.protectionSpace.serverTrust
            if (serverTrust != null) {
                // Pinning logic would go here.
                // For demonstration, we allow the challenge to proceed if trust is valid.
                completionHandler(NSURLSessionAuthChallengeUseCredential.convert(), NSURLCredential.credentialForTrust(serverTrust))
            } else {
                completionHandler(NSURLSessionAuthChallengePerformDefaultHandling.convert(), null)
            }
        }
    }
}
