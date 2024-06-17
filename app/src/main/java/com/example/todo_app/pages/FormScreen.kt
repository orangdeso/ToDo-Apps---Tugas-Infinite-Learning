package com.example.todo_app.pages

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.todo_app.R
import com.example.todo_app.model.UserModel
import com.google.firebase.database.DatabaseReference

@SuppressLint("RestrictedApi")
@Composable
fun FormScreen(modifier: Modifier = Modifier, context: android.content.Context, databaseReference: DatabaseReference) {
    // acc image storage
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

    // acc realtime database
    val name = remember {
        mutableStateOf(TextFieldValue())
    }
    val description = remember {
        mutableStateOf(TextFieldValue())
    }
    val date = remember {
        mutableStateOf(TextFieldValue())
    }

    // body
    Box(
        modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = name.value,
                onValueChange = {
                    name.value = it
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                ),
                label = { Text("Nama") }
            )

            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = description.value,
                onValueChange = {
                    description.value = it
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                ),
                label = { Text("Deskripsi") }
            )

            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = date.value,
                onValueChange = {
                    date.value = it
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                ),
                label = { Text("Input Tanggal") }
            )

            Spacer(modifier = Modifier.height(40.dp))
            Column(
                modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Tambahkan Gambar", fontSize = 18.sp)
                Spacer(modifier = Modifier.height(10.dp))
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
            }

//            Spacer(modifier = Modifier.height(30.dp))
//            TextField(
//                modifier = Modifier.fillMaxWidth(),
//                value = "",
//                onValueChange = {
//                },
//                colors = TextFieldDefaults.textFieldColors(
//                    backgroundColor = Color.White
//                ),
//                label = { Text("Tambahkan Lokasi") },
//                trailingIcon = {
//                    IconButton(onClick = {
//                        // Handle button click here
//                    }) {
//                        Icon(
//                            imageVector = Icons.Default.LocationOn,
//                            contentDescription = null,
//                            tint = Color.Gray
//                        )
//                    }
//                }
//            )

            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    val usermodel = UserModel(name.value.text, description.value.text, date.value.text)
                    databaseReference.setValue(usermodel)
                    if (imageUri != null) {
                        imageUri?.let { uri ->
                            uploadImageToFirebase(uri, context) { success ->
                                if (success) {
                                    uploadFinished = true
                                }
                            }
                        }
                    } else {
                        Toast.makeText(context, "Fail", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(contentColor = Color.White)
            ) {
                Text(text = "Upload Data")
            }
        }
    }
}
