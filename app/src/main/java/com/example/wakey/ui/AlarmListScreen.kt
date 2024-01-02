package com.example.wakey.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wakey.data.Alarm

@Composable
fun AlarmListScreen(
    modifier: Modifier = Modifier,
    wakeyViewModel: WakeyViewModel = viewModel(factory = WakeyViewModel.Factory)
) {
    wakeyViewModel.getAlarmListData()
    val uiState = wakeyViewModel.alarmListUiState.collectAsState().value

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        items(uiState.alarms) { alarm ->
            AlarmCard(
                alarm = alarm,
                patternName = uiState.patternsById[alarm.patternId]?.name ?: "No pattern"
            )
        }
    }
}

@Composable
fun AlarmCard(
    alarm: Alarm,
    patternName: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            // Alarm Title
            Text(text = alarm.name)

            // Trigger Time and Days
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                TimeDisplay(alarm = alarm)
                WeekdayIndicators(alarm = alarm)
            }

            // Snooze, Vibrate, Puzzle
            AlarmModifiers(alarm = alarm)

            // Section divider
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Divider(
                    thickness = 2.dp,
                    color = Color.Gray,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Pattern Name
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.List, contentDescription = null)
                Text(text = patternName)
            }
        }
    }
}

@Composable
fun TimeDisplay(
    alarm: Alarm,
    modifier: Modifier = Modifier
) {
    val formattedHour = if (alarm.triggerHour % 12 == 0) 12 else alarm.triggerHour % 12
    val suffix = if (alarm.triggerHour < 12) "AM" else "PM"

    Text(
        text = "$formattedHour:${alarm.triggerMinute} $suffix",
        modifier = modifier
    )
}

@Composable
fun WeekdayIndicators(
    alarm: Alarm,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier
    ) {
        WeekdayDot(dayOfWeek = "Sunday", filled = alarm.triggerOnSunday)
        WeekdayDot(dayOfWeek = "Monday", filled = alarm.triggerOnMonday)
        WeekdayDot(dayOfWeek = "Tuesday", filled = alarm.triggerOnTuesday)
        WeekdayDot(dayOfWeek = "Wednesday", filled = alarm.triggerOnWednesday)
        WeekdayDot(dayOfWeek = "Thursday", filled = alarm.triggerOnThursday)
        WeekdayDot(dayOfWeek = "Friday", filled = alarm.triggerOnFriday)
        WeekdayDot(dayOfWeek = "Saturday", filled = alarm.triggerOnSaturday)
    }
}

@Composable
fun WeekdayDot(
    dayOfWeek: String,
    filled: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        border = BorderStroke(1.dp, Color.Gray),
        colors = CardDefaults.cardColors(
            containerColor = if (filled) Color.Green else Color.Transparent
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.size(24.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = dayOfWeek.take(1),
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun AlarmModifiers(
    alarm: Alarm,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxWidth()
    ) {
        Card(
            border = BorderStroke(1.dp, Color.Gray),
            colors = CardDefaults.cardColors(
                containerColor = if (alarm.snooze) Color.Green else Color.Transparent
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = "Snooze", modifier = Modifier.padding(6.dp))
        }
        Card(
            border = BorderStroke(1.dp, Color.Gray),
            colors = CardDefaults.cardColors(
                containerColor = if (alarm.vibrate) Color.Green else Color.Transparent
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = "Vibrate", modifier = Modifier.padding(6.dp))
        }
        Card(
            border = BorderStroke(1.dp, Color.Gray),
            colors = CardDefaults.cardColors(
                containerColor = if (alarm.puzzleId != 0) Color.Green else Color.Transparent
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = "Puzzle", modifier = Modifier.padding(6.dp))
        }
    }
}

@Preview
@Composable
fun AlarmCardPreview() {
    AlarmCard(
        alarm = Alarm(
            alarmId = 0, name = "Test Alarm",
            triggerHour = 12, triggerMinute = 34,
            triggerOnSunday = false, triggerOnMonday = true, triggerOnTuesday = true,
            triggerOnWednesday = true, triggerOnThursday = true, triggerOnFriday = true,
            triggerOnSaturday = false,
            patternId = 1,
            snooze = false, vibrate = true, puzzleId = 1
        ),
        patternName = "pattern pattern"
    )
}