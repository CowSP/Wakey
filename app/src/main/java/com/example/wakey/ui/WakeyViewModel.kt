package com.example.wakey.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.wakey.WakeyApplication
import com.example.wakey.data.Alarm
import com.example.wakey.data.Pattern
import com.example.wakey.data.WakeyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AlarmListUiState(
    val alarms: List<Alarm> = listOf(),
    val patternsById: Map<Int, Pattern> = mapOf()
)

class WakeyViewModel(
    private val wakeyRepository: WakeyRepository
) : ViewModel() {
    private val _alarmListUiState = MutableStateFlow(AlarmListUiState())
    val alarmListUiState: StateFlow<AlarmListUiState> = _alarmListUiState.asStateFlow()

    fun getAlarmListData() {
        viewModelScope.launch {
            val alarms = wakeyRepository.getAllAlarms().first()
            val map = buildMap {
                alarms.forEach { alarm ->
                    val pattern = wakeyRepository.getPatternById(alarm.patternId).first()
                    put(alarm.patternId, pattern)
                }
            }

            _alarmListUiState.update {
                it.copy(
                    alarms = alarms,
                    patternsById = map
                )
            }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as WakeyApplication
                WakeyViewModel(application.container.wakeyRepository)
            }
        }
    }
}
