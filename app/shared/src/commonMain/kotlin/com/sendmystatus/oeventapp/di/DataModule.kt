package com.sendmystatus.oeventapp.di

import com.sendmystatus.oeventapp.data.repository.AuthRepository
import com.sendmystatus.oeventapp.data.storage.TokenStorage
import com.sendmystatus.oeventapp.data.storage.createTokenStorage
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val dataModule = module {
    single<Json> {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = true
            prettyPrint = true
        }
    }

    single<MutableSharedFlow<Unit>> {
        MutableSharedFlow(extraBufferCapacity = 1)
    }

    single<SharedFlow<Unit>> {
        get<MutableSharedFlow<Unit>>().asSharedFlow()
    }

    single<TokenStorage> {
        createTokenStorage()
    }
}

val repositoryModule = module {
    single {
        AuthRepository(
            client = get(),
            tokenStorage = get()
        )
    }
}
