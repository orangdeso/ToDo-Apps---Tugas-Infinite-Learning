package com.example.todo_app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todo_app.pages.AddData
import com.example.todo_app.pages.RetrieveData

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = NavigationScreen.AddData.name) {
        composable(NavigationScreen.AddData.name) {
            AddData(navController = navController)
        }
        composable(NavigationScreen.RetrieveData.name) {
            RetrieveData(navController = navController)
        }
        // add another navigation
    }
}