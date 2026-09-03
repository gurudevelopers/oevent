package com.sendmystatus.oeventapp.di

import com.sendmystatus.oeventapp.data.local.database.getDatabaseBuilder
import org.koin.dsl.module

actual fun platformModules() = listOf(
    databaseModule,
    module {
        single { getDatabaseBuilder(get()) }
    }
)
