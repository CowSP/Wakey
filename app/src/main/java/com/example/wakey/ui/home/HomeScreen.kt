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
import com.example.wakey.ui.navigation.NavigationDestination
import com.example.wakey.ui.pattern.PatternListScreen

object HomeScreenDestination : NavigationDestination {
    override val route = "home"
}

@Composable
fun HomeScreen(
    onAlarmSelected: (Int) -> Unit,
    onPatternSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = homeViewModel.homeUiState.collectAsState().value

    val navItems = listOf(
        HomeNavigationItem(
            homeSection = HomeSection.Alarms,
            icon = Icons.Default.Alarm,
            description = "Alarms"
        ),
        HomeNavigationItem(
            homeSection = HomeSection.Patterns,
            icon = Icons.Default.ViewTimeline,
            description = "Patterns"
        )
    )

    Column(modifier = modifier) {
        HomeContent(
            homeSection = uiState.currentSection,
            onAlarmSelected = onAlarmSelected,
            onPatternSelected = onPatternSelected,
            modifier = Modifier.weight(1f)
        )
        HomeNavigationBar(
            currentSection = uiState.currentSection,
            homeNavigationItems = navItems,
            onSectionPressed = homeViewModel::changeSection
        )
    }

}

@Composable
fun HomeContent(
    homeSection: HomeSection,
    onAlarmSelected: (Int) -> Unit,
    onPatternSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        when (homeSection) {
            HomeSection.Alarms -> AlarmListScreen(onAlarmSelected = onAlarmSelected)
            HomeSection.Patterns -> PatternListScreen(onPatternSelected = onPatternSelected)
        }
    }
}

@Composable
private fun HomeNavigationBar(
    currentSection: HomeSection,
    homeNavigationItems: List<HomeNavigationItem>,
    onSectionPressed: (HomeSection) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        homeNavigationItems.forEach { navItem ->
            NavigationBarItem(
                selected = currentSection == navItem.homeSection,
                onClick = { onSectionPressed(navItem.homeSection) },
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


private data class HomeNavigationItem(
    val homeSection: HomeSection,
    val icon: ImageVector,
    val description: String
)