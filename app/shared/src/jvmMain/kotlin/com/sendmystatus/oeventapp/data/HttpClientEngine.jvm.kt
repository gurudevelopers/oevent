package com.sendmystatus.oeventapp.data

import io.ktor.client.engine.*
import io.ktor.client.engine.java.*

actual fun createHttpClientEngine(): HttpClientEngine = Java.create()
