package com.sendmystatus.oeventapp.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun appModules() = listOf(
    dataModule,
    networkModule,
    repositoryModule,
    viewModelModule
)

fun initKoin(appDeclaration: KoinAppDeclaration = {}): KoinApplication =
    startKoin {
        appDeclaration()
        modules(appModules())
    }
