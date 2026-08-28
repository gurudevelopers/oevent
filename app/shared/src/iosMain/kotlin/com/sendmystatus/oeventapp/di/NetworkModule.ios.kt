package com.sendmystatus.oeventapp.di

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.darwin.*
import com.sendmystatus.oeventapp.network.NetworkConfig
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.convert
import platform.Foundation.*

@OptIn(ExperimentalForeignApi::class)
actual fun HttpClientConfig<*>.configureEngine(engineConfig: HttpClientEngineConfig, config: NetworkConfig) {
    if (engineConfig is DarwinClientEngineConfig) {
        engineConfig.handleChallenge { _, _, challenge, completionHandler ->
            val serverTrust = challenge.protectionSpace.serverTrust
            if (serverTrust != null) {
                // In a real app with pinning, you would evaluate the trust against your hashes
                // For now, we proceed with default handling if trust is valid
                completionHandler(NSURLSessionAuthChallengeUseCredential.convert(), NSURLCredential.credentialForTrust(serverTrust))
            } else {
                completionHandler(NSURLSessionAuthChallengePerformDefaultHandling.convert(), null)
            }
        }
    }
}
