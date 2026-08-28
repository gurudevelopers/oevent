package com.sendmystatus.oeventapp.di

import androidx.compose.runtime.staticCompositionLocalOf

/**
 * A CompositionLocal that provides the [SharedComponentViewModel] to the UI tree.
 * We use staticCompositionLocalOf because the DI component is typically 
 * created once at startup and never changes, which is more performant.
 */
val LocalSharedComponentViewModel = staticCompositionLocalOf<SharedComponentViewModel> {
    error("No SharedComponentViewModel provided! Make sure to wrap your app in a CompositionLocalProvider.")
}

val LocalSharedComponent = LocalSharedComponentViewModel
