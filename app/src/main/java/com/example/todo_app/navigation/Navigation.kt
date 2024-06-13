package com.example.todo_app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todo_app.pages.AddData

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = NavigationScreen.AddData.name) {
        composable(NavigationScreen.AddData.name) {
            AddData(navController = navController)
        }
        // add another navigation
    }
}