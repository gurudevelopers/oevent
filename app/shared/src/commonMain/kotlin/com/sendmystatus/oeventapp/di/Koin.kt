package com.sendmystatus.oeventapp.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.ksp.generated.module

expect fun platformModules(): List<Module>

fun appModules() = listOf(
    AppModule().module
) + platformModules()

fun initKoin(appDeclaration: KoinAppDeclaration = {}): KoinApplication =
    startKoin {
        appDeclaration()
        modules(appModules())
    }
