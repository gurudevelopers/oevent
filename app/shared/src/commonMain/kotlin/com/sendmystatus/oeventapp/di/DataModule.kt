package com.sendmystatus.oeventapp.di

import com.sendmystatus.oeventapp.data.storage.TokenStorage
import com.sendmystatus.oeventapp.data.storage.createTokenStorage
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class DataModule {

    @Single
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
        prettyPrint = true
    }

    @Single
    fun provideMutableLogoutFlow(): MutableSharedFlow<Unit> =
        MutableSharedFlow(extraBufferCapacity = 1)

    @Single
    fun provideLogoutFlow(mutableFlow: MutableSharedFlow<Unit>): SharedFlow<Unit> =
        mutableFlow.asSharedFlow()

    @Single
    fun provideTokenStorage(): TokenStorage =
        createTokenStorage()
}
