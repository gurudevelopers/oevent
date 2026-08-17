package com.sendmystatus.oeventapp.data

import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*
import okhttp3.CertificatePinner

actual fun createHttpClientEngine(): HttpClientEngine {
    val certificatePinner = CertificatePinner.Builder()
        .add("oevent.com", "sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=") // Replace with actual hash
        .add("oevent.com", "sha256/BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB=") // Backup pin
        .build()

    return OkHttp.create {
        config {
            certificatePinner(certificatePinner)
        }
    }
}
