package com.example.wakey

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wakey.ui.navigation.WakeyNavHost

@Composable
fun WakeyApp(
    navController: NavHostController = rememberNavController()
) {
    WakeyNavHost(navController = navController)
}