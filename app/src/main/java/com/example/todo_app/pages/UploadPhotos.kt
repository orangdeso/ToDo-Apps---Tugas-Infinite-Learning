@file:Suppress("ControlFlowWithEmptyBody")

package com.example.todo_app.pages

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.todo_app.R
import com.google.firebase.storage.FirebaseStorage

@Composable
fun UploadPhotos(modifier: Modifier = Modifier, navController: NavController) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var uploadFinished by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
            uploadFinished = false
        }

    LaunchedEffect(uploadFinished) {
        if (uploadFinished) {
            imageUri = null
            uploadFinished = false
        }
    }

    val painter: Painter = if (imageUri != null) {
        rememberAsyncImagePainter(imageUri)
    } else {
        painterResource(id = R.drawable.image)
    }

    Column(
        modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.None,
            modifier = Modifier
                .height(250.dp)
                .width(250.dp)
                .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp))
                .clickable {
                    launcher.launch("image/*")
                }
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                if (imageUri != null) {
                    imageUri?.let { uri ->
                        uploadImageToFirebase(uri, context) { success ->
                            if (success) {
                                uploadFinished = true
                            }
                        }
                    }
                } else {
                    Toast.makeText(context, "Select Image From Gallery", Toast.LENGTH_SHORT).show()
                }
            },
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(contentColor = Color.White)
        ) {
            Text(text = "Upload Image")
        }
    }
}

fun uploadImageToFirebase(uri: Uri, context: Context, onUploadFinished: (Boolean) -> Unit) {
    val storage = FirebaseStorage.getInstance()
    val storageReference = storage.reference
    val imageReference = storageReference.child("images/" + uri.lastPathSegment)

    val uploadTask = imageReference.putFile(uri)

    uploadTask.addOnSuccessListener {
        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        onUploadFinished(true)
    }.addOnFailureListener {
        Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show()
        onUploadFinished(false)
    }
}

@Preview
@Composable
fun Preview69() {
    UploadPhotos(navController = rememberNavController())
}
