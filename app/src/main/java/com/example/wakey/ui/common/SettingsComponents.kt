package com.example.wakey.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SelectionSetting(
    settingName: String,
    currentValue: String,
    options: List<String>,
    onSelectionChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = true }) {
            Text(text = settingName)
            Text(text = currentValue)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            options.forEachIndexed { index, option ->
                DropdownMenuItem(onClick = {
                    onSelectionChanged(index)
                    expanded = false
                }, text = { Text(text = option) })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextSetting(
    settingName: String,
    currentValue: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        label = { Text(text = settingName) },
        value = currentValue,
        onValueChange = onValueChange,
        modifier = modifier
    )
}

@Composable
fun ToggleSetting(
    settingName: String,
    currentValue: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = settingName)
        Switch(checked = currentValue, onCheckedChange = onToggle)
    }
}

@Composable
fun TimeSelector(
    currentHour: Int,
    currentMinute: Int,
    onValueChange: (Int, Int) -> Unit,
    modifier: Modifier = Modifier,
    use12HourFormat: Boolean = false,
) {
    // Selection options
    val hours24 = buildList {
        for (i in 0..23) {
            add(i.toString())
        }
    }
    val hours12 = buildList {
        add("12")
        for (i in 1..11) {
            add(i.toString())
        }
    }
    val minutes = buildList {
        for (i in 0..59) {
            add(i.toString().padStart(2, '0'))
        }
    }
    val meridiems = listOf("AM", "PM")

    // Selection wheels
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null)
        if (use12HourFormat) {
            CircularColumn(
                currentIndex = currentHour,
                items = hours12,
                onValueChange = { onValueChange(currentHour / 12 * 12 + it, currentMinute) },
                modifier = Modifier.width(80.dp)
            )
            Text(text = ":", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            CircularColumn(
                currentIndex = currentMinute,
                items = minutes,
                onValueChange = { onValueChange(currentHour, it) },
                modifier = Modifier.width(80.dp)
            )
            CircularColumn(
                currentIndex = if (currentHour >= 12) 1 else 0, items = meridiems, onValueChange = {
                    val hour = currentHour % 12 + it * 12
                    onValueChange(hour, currentMinute)
                }, displayedItems = 3, modifier = Modifier.width(80.dp)
            )
        } else {
            CircularColumn(
                currentIndex = currentHour,
                items = hours24,
                onValueChange = { onValueChange(it, currentMinute) },
                modifier = Modifier.weight(1f)
            )
            Text(text = ":")
            CircularColumn(
                currentIndex = currentMinute,
                items = minutes,
                onValueChange = { onValueChange(currentHour, it) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CircularColumn(
    currentIndex: Int,
    items: List<String>,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    itemHeight: Dp = 28.dp,
    displayedItems: Int = 4
) {
    val itemHeightHalf = LocalDensity.current.run { itemHeight.toPx() / 2f }
    val scrollState = rememberLazyListState(0)
    var lastSelectedIndex by remember { mutableStateOf(0) }

    LaunchedEffect(items) {
        // Integer divide then multiply to index of a 0th item
        var targetIndex = Int.MAX_VALUE / 2 / items.size * items.size

        // Offset based on current value and screen positioning
        targetIndex += currentIndex
        targetIndex -= displayedItems / 2
        scrollState.scrollToItem(targetIndex)
        if (displayedItems % 2 == 0) {
            scrollState.scrollBy(itemHeightHalf)
        }

        // Store index of selected item
        lastSelectedIndex = targetIndex
    }

    LazyColumn(
        state = scrollState,
        flingBehavior = rememberSnapFlingBehavior(lazyListState = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .wrapContentWidth()
            .height(itemHeight * displayedItems)
    ) {
        items(Int.MAX_VALUE) { i ->
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(itemHeight)
                    .onGloballyPositioned { coordinates ->
                        val yOffset = coordinates.positionInParent().y - itemHeightHalf
                        val yMidpoint = itemHeightHalf * displayedItems
                        val isSelected =
                            (yOffset > yMidpoint - itemHeightHalf) && (yOffset < yMidpoint + itemHeightHalf)
                        val index = i - 1

                        if (isSelected && index != lastSelectedIndex) {
                            onValueChange(index % items.size)
                            lastSelectedIndex = index
                        }
                    }) {
                Text(
                    text = items[i % items.size],
                    fontSize = if (lastSelectedIndex == i) 24.sp else 16.sp
                )
            }
        }
    }
}

@Composable
fun WeekdaySelector(
    settingName: String?,
    weekdayStatus: List<Boolean>,
    onValueChange: (List<Boolean>) -> Unit,
    modifier: Modifier = Modifier
) {
    val weekdayStatusMutable = weekdayStatus.toMutableList()
    val weekdayInitials = listOf("S", "M", "T", "W", "T", "F", "S")

    Column(modifier = modifier.fillMaxWidth()) {
        if (settingName != null) {
            Text(text = settingName)
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 8.dp)
        ) {
            weekdayStatus.forEachIndexed { day, status ->
                WeekdayButton(weekdayStatus = status,
                    weekdayInitial = weekdayInitials[day],
                    onButtonPressed = {
                        weekdayStatusMutable[day] = !weekdayStatusMutable[day]
                        onValueChange(weekdayStatusMutable)
                    })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeekdayButton(
    weekdayStatus: Boolean,
    weekdayInitial: String,
    onButtonPressed: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    if (weekdayStatus) {
        // If enabled on the given weekday
        Surface(
            onClick = { onButtonPressed(false) },
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(1.dp, Color.Gray),
            color = Color.Green,
            modifier = modifier.size(48.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = weekdayInitial)
            }
        }
    } else {
        // If disabled on the given weekday
        Surface(
            onClick = { onButtonPressed(true) },
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(1.dp, Color.Gray),
            modifier = modifier.size(48.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = weekdayInitial)
            }
        }
    }
}