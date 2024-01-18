package com.example.wakey.ui.alarm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wakey.data.Alarm
import com.example.wakey.data.Pattern
import com.example.wakey.data.Puzzle
import com.example.wakey.data.WakeyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

data class AlarmDetailUiState(
    val alarmId: Int = 0,
    val name: String = "",
    val triggerHour: Int = 0,
    val triggerMinute: Int = 0,
    val triggerDays: List<Boolean> = List(7) { false },
    val patternId: Int = 0,
    val snooze: Boolean = false,
    val vibrate: Boolean = false,
    val puzzleId: Int = 0,
    val currentPattern: Pattern = Pattern(0, ""),
    val availablePatterns: List<Pattern> = emptyList(),
    val currentPuzzle: Puzzle = Puzzle(0, ""),
    val availablePuzzles: List<Puzzle> = emptyList()
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
    savedStateHandle: SavedStateHandle, private val wakeyRepository: WakeyRepository
) : ViewModel() {
    private val _alarmDetailUiState = MutableStateFlow(AlarmDetailUiState())
    val alarmDetailUiState: StateFlow<AlarmDetailUiState> = _alarmDetailUiState.asStateFlow()

    init {
        runBlocking {
            // Load alarm details
            val selectedAlarmId: Int =
                checkNotNull(savedStateHandle[AlarmDetailScreenDestination.alarmIdArg])
            val selectedAlarm = wakeyRepository.getAlarmById(selectedAlarmId).first()
            _alarmDetailUiState.update {
                selectedAlarm.toAlarmDetailUiState()
            }

            // Load objects for current pattern, puzzle
            val currentPattern = wakeyRepository.getPatternById(selectedAlarm.patternId).first()
            val currentPuzzle = wakeyRepository.getPuzzleById(selectedAlarm.puzzleId).first()
            _alarmDetailUiState.update {
                it.copy(currentPattern = currentPattern, currentPuzzle = currentPuzzle)
            }

            // Load pattern options
            val availablePatterns = wakeyRepository.getAllPatterns().first()
            _alarmDetailUiState.update {
                it.copy(availablePatterns = availablePatterns)
            }

            // Load puzzle options
            val availablePuzzles = wakeyRepository.getAllPuzzles().first()
            _alarmDetailUiState.update {
                it.copy(availablePuzzles = availablePuzzles)
            }
        }
    }

    fun updateUiState(newUiState: AlarmDetailUiState) {
        viewModelScope.launch {
            _alarmDetailUiState.update { newUiState }

            if (newUiState.patternId != _alarmDetailUiState.value.currentPattern.patternId) {
                val selectedPattern = wakeyRepository.getPatternById(newUiState.patternId).first()
                _alarmDetailUiState.update { it.copy(currentPattern = selectedPattern) }
            }

            if (newUiState.puzzleId != _alarmDetailUiState.value.currentPuzzle.puzzleId) {
                val selectedPuzzle = wakeyRepository.getPuzzleById(newUiState.puzzleId).first()
                _alarmDetailUiState.update { it.copy(currentPuzzle = selectedPuzzle) }
            }
        }
    }

    fun updateAlarmDetails() {
        viewModelScope.launch {
            val updatedAlarm = alarmDetailUiState.value.toAlarm()
            wakeyRepository.editAlarm(updatedAlarm)
        }
    }
}