package com.sendmystatus.oeventapp.di

import com.sendmystatus.oeventapp.ui.viewmodel.*
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::AuthViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::OnboardingViewModel)
    viewModelOf(::CreateEventViewModel)
    viewModelOf(::EventProgramViewModel)
    viewModelOf(::AddProgramViewModel)
    viewModelOf(::EventSettingViewModel)
    viewModelOf(::EventTemplateViewModel)
}
