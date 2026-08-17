package com.sendmystatus.oeventapp.data

import io.ktor.client.engine.*
import io.ktor.client.engine.js.*

actual fun createHttpClientEngine(): HttpClientEngine = Js.create()
