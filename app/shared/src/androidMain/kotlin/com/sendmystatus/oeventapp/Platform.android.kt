package com.sendmystatus.oeventapp

import android.os.Build
import kotlin.system.exitProcess

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun closeApp() {
    //exitProcess(0)


}