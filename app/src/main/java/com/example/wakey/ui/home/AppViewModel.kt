package com.example.wakey.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class AppSection {
    Alarms, Patterns
}

data class AppUiState(
    val currentSection: AppSection = AppSection.Alarms
)

class AppViewModel : ViewModel() {
    private val _appUiState = MutableStateFlow(AppUiState())
    val appUiState: StateFlow<AppUiState> = _appUiState.asStateFlow()

    fun changeSection(section: AppSection) {
        _appUiState.update {
            it.copy(currentSection = section)
        }
    }
}