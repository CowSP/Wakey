package com.example.wakey.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class HomeSection {
    Alarms, Patterns
}

data class HomeUiState(
    val currentSection: HomeSection = HomeSection.Alarms
)

class HomeViewModel : ViewModel() {
    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()

    fun changeSection(section: HomeSection) {
        _homeUiState.update {
            it.copy(currentSection = section)
        }
    }
}