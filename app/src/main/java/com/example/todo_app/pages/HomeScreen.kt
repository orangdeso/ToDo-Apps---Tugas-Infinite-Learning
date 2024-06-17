package com.example.todo_app.pages

import android.graphics.Paint.Align
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.todo_app.navigation.NavigationScreen

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                navController.navigate(NavigationScreen.FormScreen.name)
            },
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
        ) {
            Text(text = "Upload Photo Form", color = Color.White)
        }

        Button(
            onClick = {
                navController.navigate(NavigationScreen.RetrievePhotos.name)
            },
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
        ) {
            Text(text = "Retrieve Photo", color = Color.White)
        }
    }
}

@Preview
@Composable
fun Preview22() {
    HomeScreen(navController = rememberNavController())
}