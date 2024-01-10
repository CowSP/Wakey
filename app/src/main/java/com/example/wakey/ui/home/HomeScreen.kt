package com.example.wakey.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.ViewTimeline
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wakey.ui.AppViewModelProvider
import com.example.wakey.ui.alarm.AlarmListScreen
import com.example.wakey.ui.pattern.PatternListScreen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = appViewModel.appUiState.collectAsState().value

    val navItems = listOf(
        NavigationItem(
            appSection = AppSection.Alarms,
            icon = Icons.Default.Alarm,
            description = "Alarms"
        ),
        NavigationItem(
            appSection = AppSection.Patterns,
            icon = Icons.Default.ViewTimeline,
            description = "Patterns"
        )
    )

    Column(modifier = modifier) {
        AppContent(appSection = uiState.currentSection, modifier = Modifier.weight(1f))
        WakeyNavigationBar(
            currentSection = uiState.currentSection,
            navigationItems = navItems,
            onSectionPressed = appViewModel::changeSection
        )
    }

}

@Composable
fun AppContent(
    appSection: AppSection,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        when (appSection) {
            AppSection.Alarms -> AlarmListScreen()
            AppSection.Patterns -> PatternListScreen()
        }
    }
}

@Composable
private fun WakeyNavigationBar(
    currentSection: AppSection,
    navigationItems: List<NavigationItem>,
    onSectionPressed: (AppSection) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        navigationItems.forEach { navItem ->
            NavigationBarItem(
                selected = currentSection == navItem.appSection,
                onClick = { onSectionPressed(navItem.appSection) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.description
                    )
                },
                label = {
                    Text(text = navItem.description)
                }
            )
        }
    }
}


private data class NavigationItem(
    val appSection: AppSection,
    val icon: ImageVector,
    val description: String
)