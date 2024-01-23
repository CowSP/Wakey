package com.example.wakey.ui.pattern

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wakey.data.Pattern
import com.example.wakey.data.Tick
import com.example.wakey.data.Tone
import com.example.wakey.ui.AppViewModelProvider

@Composable
fun PatternListScreen(
    onPatternSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    patternListViewModel: PatternListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    patternListViewModel.loadPatternListData()
    val uiState = patternListViewModel.patternListUiState.collectAsState().value

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp), modifier = modifier
    ) {
        items(uiState.patterns) { pattern ->
            PatternCard(pattern = pattern, uiState = uiState, modifier = Modifier.clickable { onPatternSelected(pattern.patternId) })
        }
    }
}

@Composable
fun PatternCard(
    pattern: Pattern, uiState: PatternListUiState, modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(8.dp)
        ) {
            Text(text = pattern.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            TickList(ticks = uiState.ticksByPatternId[pattern.patternId]!!, uiState = uiState)
        }
    }
}

@Composable
fun TickList(
    ticks: List<Tick>, uiState: PatternListUiState, modifier: Modifier = Modifier
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = modifier) {
        items(ticks) { tick ->
            TickCard(tick = tick, uiState = uiState)
        }
    }
}

@Composable
fun TickCard(
    tick: Tick, uiState: PatternListUiState, modifier: Modifier = Modifier
) {
    val formattedOffset = "+${tick.delayMinutes} min"
    val toneName = uiState.tonesById[tick.toneId]!!.name

    Card(border = BorderStroke(1.dp, Color.Gray), modifier = modifier.width(120.dp)) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(8.dp, 8.dp, 14.dp, 8.dp)
        ) {
            Text(text = formattedOffset, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.MusicNote, contentDescription = null)
                Text(text = toneName, softWrap = false, overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.size(0.dp))
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.VolumeUp, contentDescription = null)
                LinearProgressIndicator(
                    progress = tick.volume / 100f, trackColor = Color.LightGray
                )
            }
        }
    }
}

@Preview
@Composable
fun PatternCardPreview() {
    PatternCard(
        pattern = Pattern(10, "Wake Up!!!"), uiState = PatternListUiState(
            ticksByPatternId = mapOf(
                Pair(
                    10,
                    listOf(Tick(1, 10, 10, 1, 30), Tick(2, 10, 15, 2, 50), Tick(3, 10, 20, 5, 10))
                )
            ), tonesById = mapOf(
                Pair(1, Tone(1, "Generic Tone", "")),
                Pair(2, Tone(2, "Generic Other Tone", "")),
                Pair(5, Tone(5, "Tone", ""))
            )
        ), modifier = Modifier.width(240.dp)
    )
}

@Preview
@Composable
fun TickCardPreview() {
    TickCard(
        tick = Tick(0, 0, 5, 123, 75), uiState = PatternListUiState(
            tonesById = mapOf(
                Pair(123, Tone(123, "Ring Ring", ""))
            )
        )
    )
}
