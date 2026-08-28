package com.sendmystatus.oeventapp.di

import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import me.tatarka.inject.annotations.Scope
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import com.sendmystatus.oeventapp.ui.viewmodel.*

@Scope
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class AppScope

@Component
@AppScope
abstract class SharedComponent {
    
    abstract val authViewModel: AuthViewModel
    abstract val profileViewModel: ProfileViewModel
    abstract val onboardingViewModel: OnboardingViewModel
    abstract val createEventViewModel: CreateEventViewModel
    abstract val eventProgramViewModel: EventProgramViewModel
    abstract val addProgramViewModel: AddProgramViewModel
    abstract val eventSettingViewModel: EventSettingViewModel
    abstract val eventTemplateViewModel: EventTemplateViewModel

    @Provides
    @AppScope
    fun provideHttpClient(): HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    companion object
}

expect fun createComponent(): SharedComponent
