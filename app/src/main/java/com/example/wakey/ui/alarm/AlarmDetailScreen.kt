package com.example.wakey.ui.alarm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wakey.data.Pattern
import com.example.wakey.data.Puzzle
import com.example.wakey.ui.AppViewModelProvider
import com.example.wakey.ui.common.SelectionSetting
import com.example.wakey.ui.common.TextSetting
import com.example.wakey.ui.common.TimeSelector
import com.example.wakey.ui.common.ToggleSetting
import com.example.wakey.ui.common.WeekdaySelector
import com.example.wakey.ui.navigation.NavigationDestination

object AlarmDetailScreenDestination : NavigationDestination {
    override val route = "alarm_edit"
    const val alarmIdArg = "alarmId"
    val routeWithArgs = "$route/{$alarmIdArg}"
}

@Composable
fun AlarmDetailScreen(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AlarmDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.alarmDetailUiState.collectAsState().value

    Column(modifier = modifier) {
        AlarmDetailForm(
            uiState = uiState,
            onValueChange = viewModel::updateUiState,
            modifier = Modifier.weight(1f)
        )
        AlarmDetailButtons(onCancelPressed = navigateUp, onSavePressed = {
            viewModel.updateAlarmDetails()
            navigateUp()
        })
    }
}

@Composable
fun AlarmDetailForm(
    uiState: AlarmDetailUiState,
    onValueChange: (AlarmDetailUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Trigger time
        TimeSelector(
            use12HourFormat = true,
            currentHour = uiState.triggerHour,
            currentMinute = uiState.triggerMinute,
            onValueChange = { hour, minute ->
                onValueChange(
                    uiState.copy(
                        triggerHour = hour, triggerMinute = minute
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Alarm name
        TextSetting(
            settingName = "Alarm name",
            currentValue = uiState.name,
            onValueChange = { onValueChange(uiState.copy(name = it)) },
            modifier = Modifier.fillMaxWidth()
        )

        // Active days
        WeekdaySelector(settingName = "Active on days",
            weekdayStatus = uiState.triggerDays,
            onValueChange = { onValueChange(uiState.copy(triggerDays = it)) })

        // Alarm pattern
        SelectionSetting(
            settingName = "Alarm pattern",
            currentValue = uiState.currentPattern.name,
            options = uiState.availablePatterns.map { it.name },
            onSelectionChanged = {
                val selectedPattern = uiState.availablePatterns[it]
                onValueChange(uiState.copy(patternId = selectedPattern.patternId))
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Snooze
        ToggleSetting(settingName = "Snooze",
            currentValue = uiState.snooze,
            onToggle = { onValueChange(uiState.copy(snooze = it)) })

        // Vibrate
        ToggleSetting(settingName = "Vibrate",
            currentValue = uiState.vibrate,
            onToggle = { onValueChange(uiState.copy(vibrate = it)) })

        // Puzzle
        SelectionSetting(settingName = "Puzzle",
            currentValue = uiState.currentPuzzle.name,
            options = uiState.availablePuzzles.map { it.name },
            onSelectionChanged = {
                val selectedPuzzle = uiState.availablePuzzles[it]
                onValueChange(uiState.copy(puzzleId = selectedPuzzle.puzzleId))
            })
    }
}

@Composable
fun AlarmDetailButtons(
    onCancelPressed: () -> Unit, onSavePressed: () -> Unit, modifier: Modifier = Modifier
) {
    Row(horizontalArrangement = Arrangement.SpaceAround, modifier = modifier.fillMaxWidth()) {
        OutlinedButton(onClick = onCancelPressed) {
            Text(text = "Cancel")
        }
        Button(onClick = onSavePressed) {
            Text(text = "Save")
        }
    }
}

@Preview
@Composable
fun AlarmDetailFormPreview() {
    AlarmDetailForm(
        uiState = AlarmDetailUiState(
            name = "Morning alarm",
            triggerDays = listOf(false, true, true, false, true, true, false),
            snooze = true,
            currentPattern = Pattern(1, "Example pattern"),
            currentPuzzle = Puzzle(1, "Puzzle example")
        ), onValueChange = {}, modifier = Modifier.background(Color.White)
    )
}