package com.example.wakey.ui.pattern

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wakey.data.Pattern
import com.example.wakey.data.Tick
import com.example.wakey.data.Tone
import com.example.wakey.data.WakeyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PatternListUiState(
    val patterns: List<Pattern> = listOf(),
    val ticksByPatternId: Map<Int, List<Tick>> = mapOf(),
    val tonesById: Map<Int, Tone> = mapOf()
)

class PatternListViewModel(
    private val wakeyRepository: WakeyRepository
) : ViewModel() {
    private val _patternListUiState = MutableStateFlow(PatternListUiState())
    val patternListUiState: StateFlow<PatternListUiState> = _patternListUiState.asStateFlow()

    fun loadPatternListData() {
        viewModelScope.launch {
            val patterns = wakeyRepository.getAllPatterns().first()
            val tickMap = buildMap {
                patterns.forEach { pattern ->
                    val ticks = wakeyRepository.getTicksByPatternId(pattern.patternId).first()
                    put(pattern.patternId, ticks)
                }
            }
            val toneMap = buildMap {
                tickMap.values.forEach { tickList ->
                    tickList.forEach { tick ->
                        val tone = wakeyRepository.getToneById(tick.toneId).first()
                        put(tone.toneId, tone)
                    }
                }
            }

            _patternListUiState.update {
                it.copy(
                    patterns = patterns,
                    ticksByPatternId = tickMap,
                    tonesById = toneMap
                )
            }

        }
    }
}