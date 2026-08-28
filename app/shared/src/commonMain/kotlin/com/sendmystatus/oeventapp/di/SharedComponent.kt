package com.sendmystatus.oeventapp.di

import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import me.tatarka.inject.annotations.Scope
import kotlinx.serialization.json.Json
import com.sendmystatus.oeventapp.ui.viewmodel.*
import com.sendmystatus.oeventapp.network.NetworkConfig
import com.sendmystatus.oeventapp.data.storage.TokenStorage
import com.sendmystatus.oeventapp.data.repository.AuthRepository
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.SharedFlow

@Scope
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class AppScope

@Component
@AppScope
abstract class NetworkComponent(
    @get:Provides val networkConfig: NetworkConfig,
    @get:Provides val tokenStorage: TokenStorage
) : NetworkModule, DataModule {
    abstract val httpClient: HttpClient
    abstract override val logoutFlow: SharedFlow<Unit>
    
    companion object
}

@Component
@AppScope
abstract class RepositoryComponent(
    @get:Provides val httpClient: HttpClient,
    @get:Provides val tokenStorage: TokenStorage
) : StorageModule {
    abstract val authRepository: AuthRepository
    
    companion object
}

@Component
@AppScope
abstract class SharedComponentViewModel : ViewModelModule {
    
    companion object
}
