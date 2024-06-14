package com.example.todo_app.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.storage.FirebaseStorage

@Composable
fun RetrievePhotos(modifier: Modifier = Modifier, navController: NavController) {
    var imageUrl by remember { mutableStateOf(listOf<String>()) }
    LaunchedEffect(Unit) {
        getImageUrls { urls ->
            imageUrl = urls
        }
    }

    Column(
        modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LazyColumn {
            items(imageUrl) { url ->
                ImageItem(url)
            }
        }
    }
}

@Composable
fun ImageItem(url: String) {
    Image(
        painter = rememberAsyncImagePainter(url),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp), // Adjust as necessary
        contentScale = ContentScale.Crop
    )
}

private fun getImageUrls(callback: (List<String>) -> Unit) {
    val storage = FirebaseStorage.getInstance()
    val listRef = storage.reference.child("images/")

    listRef.listAll()
        .addOnSuccessListener { listResult ->
            val urls = mutableListOf<String>()
            listResult.items.forEach { item ->
                item.downloadUrl.addOnSuccessListener { uri ->
                    urls.add(uri.toString())
                    if (urls.size == listResult.items.size) {
                        callback(urls)
                    }
                }
            }
        }
        .addOnFailureListener {
            // Handle any errors
        }
}


