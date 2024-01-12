package com.example.wakey.ui.alarm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wakey.data.Alarm
import com.example.wakey.data.WakeyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AlarmDetailUiState(
    val alarmId: Int = 0,
    val name: String = "",
    val triggerHour: Int = 0,
    val triggerMinute: Int = 0,
    val triggerDays: List<Boolean> = List(7) { false },
    val patternId: Int = 0,
    val snooze: Boolean = false,
    val vibrate: Boolean = false,
    val puzzleId: Int = 0
)

fun Alarm.toAlarmDetailUiState(): AlarmDetailUiState = AlarmDetailUiState(
    alarmId = alarmId,
    name = name,
    triggerHour = triggerHour,
    triggerMinute = triggerMinute,
    triggerDays = listOf(
        triggerOnSunday,
        triggerOnMonday,
        triggerOnTuesday,
        triggerOnWednesday,
        triggerOnThursday,
        triggerOnFriday,
        triggerOnSaturday
    ),
    patternId = patternId,
    snooze = snooze,
    vibrate = vibrate,
    puzzleId = puzzleId
)

fun AlarmDetailUiState.toAlarm(): Alarm = Alarm(
    alarmId = alarmId,
    name = name,
    triggerHour = triggerHour,
    triggerMinute = triggerMinute,
    triggerOnSunday = triggerDays[0],
    triggerOnMonday = triggerDays[1],
    triggerOnTuesday = triggerDays[2],
    triggerOnWednesday = triggerDays[3],
    triggerOnThursday = triggerDays[4],
    triggerOnFriday = triggerDays[5],
    triggerOnSaturday = triggerDays[6],
    patternId = patternId,
    snooze = snooze,
    vibrate = vibrate,
    puzzleId = puzzleId
)

class AlarmDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val wakeyRepository: WakeyRepository
) : ViewModel() {
    private val _alarmDetailUiState = MutableStateFlow(AlarmDetailUiState())
    val alarmDetailUiState: StateFlow<AlarmDetailUiState> = _alarmDetailUiState.asStateFlow()

    init {
        viewModelScope.launch {
            val selectedAlarmId: Int =
                checkNotNull(savedStateHandle[AlarmDetailScreenDestination.alarmIdArg])
            val selectedAlarm = wakeyRepository.getAlarmById(selectedAlarmId).first()

            _alarmDetailUiState.update {
                selectedAlarm.toAlarmDetailUiState()
            }
        }
    }
}