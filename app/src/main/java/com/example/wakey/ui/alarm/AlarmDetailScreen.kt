package com.example.wakey.ui.alarm

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wakey.ui.AppViewModelProvider
import com.example.wakey.ui.navigation.NavigationDestination

object AlarmDetailScreenDestination : NavigationDestination {
    override val route = "alarm_edit"
    const val alarmIdArg = "alarmId"
    val routeWithArgs = "$route/{$alarmIdArg}"
}

@Composable
fun AlarmDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: AlarmDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val selectedAlarm = viewModel.alarmDetailUiState.collectAsState().value.toAlarm()
    Text(text = selectedAlarm.toString())
}