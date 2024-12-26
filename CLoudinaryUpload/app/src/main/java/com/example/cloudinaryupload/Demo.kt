package com.example.cloudinaryupload

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.cloudinary.Cloudinary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@Composable
fun CloudinaryImageUploader() {
    val context = LocalContext.current
    var imageUrl by remember { mutableStateOf<String?>(null) }
    var isUploading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.sample),
            contentDescription = "Placeholder Image",
            modifier = Modifier.size(150.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (!isUploading) {
                    isUploading = true
                    coroutineScope.launch {
                        uploadImageToCloudinary(context) { url ->
                            isUploading = false
                            imageUrl = url
                        }
                    }
                }
            },
            enabled = !isUploading,
            modifier = Modifier.wrapContentSize()
        ) {
            Text(text = if (isUploading) "Uploading..." else "Upload Image")
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (imageUrl != null) {
            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = "Uploaded Image from Cloudinary",
                modifier = Modifier.size(150.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                imageUrl = imageUrl
            },
            enabled = imageUrl != null,
            modifier = Modifier.wrapContentSize()
        ) {
            Text(text = "Load Image")
        }
    }
}

@SuppressLint("AuthLeak")
suspend fun uploadImageToCloudinary(context: Context, onResult: (String?) -> Unit) {
    val cloudinary = Cloudinary("cloudinary://277755497182713:9SQ1y1AZ04niTxP86F6-PLt0wvE@dw25apgd7")

    withContext(Dispatchers.IO) {
        try {
            val drawable = context.getDrawable(R.drawable.sample)
            val bitmap = (drawable as? android.graphics.drawable.BitmapDrawable)?.bitmap

            val tempFile = File(context.cacheDir, "temp_image.png")
            val outputStream = tempFile.outputStream()
            bitmap?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.close()

            val uploadResult = cloudinary.uploader().upload(tempFile.path, mapOf(
                "public_id" to "uploaded_image",
                "use_filename" to true,
                "resource_type" to "image"
            ))

            val url = uploadResult["secure_url"] as String?
            withContext(Dispatchers.Main) {
                onResult(url)
                Toast.makeText(context, "Upload successful!", Toast.LENGTH_SHORT).show()
            }

            tempFile.delete()

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Upload failed: ${e.message}", Toast.LENGTH_LONG).show()
                onResult(null)
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewDemo() {
    CloudinaryImageUploader()
}
