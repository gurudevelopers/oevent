package com.sendmystatus.oeventapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform