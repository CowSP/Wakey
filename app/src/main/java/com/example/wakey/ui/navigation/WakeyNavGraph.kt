package com.example.wakey.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.wakey.ui.alarm.AlarmDetailScreen
import com.example.wakey.ui.alarm.AlarmDetailScreenDestination
import com.example.wakey.ui.home.HomeScreen
import com.example.wakey.ui.home.HomeScreenDestination
import com.example.wakey.ui.pattern.PatternDetailScreen
import com.example.wakey.ui.pattern.PatternDetailScreenDestination

@Composable
fun WakeyNavHost(
    navController: NavHostController, modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeScreenDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeScreenDestination.route) {
            HomeScreen(
                onAlarmSelected = { navController.navigate("${AlarmDetailScreenDestination.route}/$it") },
                onPatternSelected = { navController.navigate("${PatternDetailScreenDestination.route}/$it") })
        }

        composable(
            route = AlarmDetailScreenDestination.routeWithArgs,
            arguments = listOf(navArgument(AlarmDetailScreenDestination.alarmIdArg) {
                type = NavType.IntType
            })
        ) {
            AlarmDetailScreen(navigateUp = { navController.navigateUp() })
        }
        
        composable(
            route = PatternDetailScreenDestination.routeWithArgs,
            arguments = listOf(navArgument(PatternDetailScreenDestination.patternIdArg) {
                type = NavType.IntType
            })
        ) {
            PatternDetailScreen(navigateUp = { navController.navigateUp() })
        }
    }
}