package com.sendmystatus.oeventapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class EventTemplateViewModel : ViewModel() {
    private val _selectedTemplate = MutableStateFlow("")
    val selectedTemplate = _selectedTemplate.asStateFlow()

    fun selectTemplate(template: String) {
        _selectedTemplate.update { 
            if (it == template) "" else template
        }
    }
}
