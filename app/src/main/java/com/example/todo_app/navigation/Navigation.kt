package com.example.todo_app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todo_app.pages.HomeScreen
import com.example.todo_app.pages.RetrievePhotos
import com.example.todo_app.pages.UploadPhotos

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = NavigationScreen.HomeScreen.name) {
        composable(NavigationScreen.UploadPhotos.name) {
            UploadPhotos(navController = navController)
        }
        composable(NavigationScreen.RetrievePhotos.name) {
            RetrievePhotos(navController = navController)
        }
        composable(NavigationScreen.HomeScreen.name) {
            HomeScreen(navController = navController)
        }
        // add another navigation
    }
}