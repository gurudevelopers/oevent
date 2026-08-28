package com.sendmystatus.oeventapp.di

import com.sendmystatus.oeventapp.ui.viewmodel.*

interface ViewModelModule {
    
    val authViewModel: AuthViewModel
    val profileViewModel: ProfileViewModel
    val onboardingViewModel: OnboardingViewModel
    val createEventViewModel: CreateEventViewModel
    val eventProgramViewModel: EventProgramViewModel
    val addProgramViewModel: AddProgramViewModel
    val eventSettingViewModel: EventSettingViewModel
    val eventTemplateViewModel: EventTemplateViewModel

    companion object
}
