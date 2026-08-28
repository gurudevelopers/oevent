package com.sendmystatus.oeventapp.di

import io.ktor.client.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Provides

interface DataModule {

    val logoutFlow: SharedFlow<Unit>

    @Provides
    @AppScope
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
        prettyPrint = true
    }

    @Provides
    @AppScope
    fun provideLogoutFlow(): MutableSharedFlow<Unit> = MutableSharedFlow(extraBufferCapacity = 1)

    @Provides
    @AppScope
    fun providePublicLogoutFlow(mutableLogoutFlow: MutableSharedFlow<Unit>): SharedFlow<Unit> =
        mutableLogoutFlow.asSharedFlow()

    companion object
}
