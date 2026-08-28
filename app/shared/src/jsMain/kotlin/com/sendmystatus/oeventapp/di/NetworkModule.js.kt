package com.sendmystatus.oeventapp.di

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.js.*
import com.sendmystatus.oeventapp.network.NetworkConfig

actual fun HttpClientConfig<*>.configureEngine(engineConfig: HttpClientEngineConfig, config: NetworkConfig) {
    // JS specific configuration
}
