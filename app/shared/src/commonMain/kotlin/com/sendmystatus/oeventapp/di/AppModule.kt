package com.sendmystatus.oeventapp.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [DataModule::class, NetworkModule::class])
@ComponentScan("com.sendmystatus.oeventapp")
class AppModule
