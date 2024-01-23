package com.example.wakey.ui.pattern

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.wakey.data.Tick
import com.example.wakey.data.Tone
import com.example.wakey.data.WakeyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking

data class PatternDetailUiState(
    val patternId: Int = 0,
    val name: String = "",
    val ticks: List<Tick> = emptyList(),
    val toneMap: Map<Int, Tone> = emptyMap()
)

class PatternDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val wakeyRepository: WakeyRepository
) : ViewModel() {
    private val _patternDetailUiState = MutableStateFlow(PatternDetailUiState())
    val patternDetailUiState: StateFlow<PatternDetailUiState> = _patternDetailUiState.asStateFlow()

    init {
        runBlocking {
            val selectedPatternId: Int = checkNotNull(savedStateHandle[PatternDetailScreenDestination.patternIdArg])
            val selectedPattern = wakeyRepository.getPatternById(selectedPatternId).first()

            val ticks = wakeyRepository.getTicksByPatternId(selectedPatternId).first()
            val toneMap = ticksToTones(ticks, wakeyRepository)

            _patternDetailUiState.update {
                PatternDetailUiState(patternId = selectedPatternId, name = selectedPattern.name, ticks = ticks, toneMap = toneMap)
            }
        }
    }
}

suspend fun ticksToTones(ticks: List<Tick>, wakeyRepository: WakeyRepository) : Map<Int, Tone> {
    return buildMap {
        ticks.forEach {tick ->
            val tone = wakeyRepository.getToneById(tick.toneId).first()
            put(tone.toneId, tone)
        }
    }
}