package com.example.todo_app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todo_app.pages.FormScreen
import com.example.todo_app.pages.HomeScreen
import com.example.todo_app.pages.RetrievePhotos
import com.example.todo_app.pages.UploadPhotos
import com.google.firebase.database.FirebaseDatabase

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val firebaseDatabase = FirebaseDatabase.getInstance();
    val databaseReference = firebaseDatabase.getReference("dataToDoApp");

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
        composable(NavigationScreen.FormScreen.name) {
            FormScreen(context = LocalContext.current, databaseReference = databaseReference)
        }
        // add another navigation
    }
}