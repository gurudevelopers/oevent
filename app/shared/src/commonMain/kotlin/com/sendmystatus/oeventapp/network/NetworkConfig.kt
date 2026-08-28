package com.sendmystatus.oeventapp.network

data class NetworkConfig(
    val baseUrl: String,
    val wsUrl: String,
    val timeoutMillis: Long = 30_000,
    val retryCount: Int = 3,
    val isDebug: Boolean = true,
    val pinningHashes: List<String> = emptyList()
)
