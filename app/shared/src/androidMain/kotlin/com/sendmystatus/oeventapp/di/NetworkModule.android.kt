package com.sendmystatus.oeventapp.di

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*
import okhttp3.CertificatePinner
import com.sendmystatus.oeventapp.network.NetworkConfig

actual fun HttpClientConfig<*>.configureEngine(engineConfig: HttpClientEngineConfig, config: NetworkConfig) {
    if (engineConfig is OkHttpConfig) {
        engineConfig.config {
            if (config.pinningHashes.isNotEmpty()) {
                val pinner = CertificatePinner.Builder()
                config.pinningHashes.forEach { hash ->
                    // Extract domain from baseUrl
                    val domain = config.baseUrl.substringAfter("://").substringBefore("/")
                    pinner.add(domain, hash)
                }
                certificatePinner(pinner.build())
            }
        }
    }
}
