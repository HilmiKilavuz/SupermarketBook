package com.kilavuzhilmi.supermarketbook.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import com.kilavuzhilmi.supermarketbook.R
import com.kilavuzhilmi.supermarketbook.model.Item
import java.io.ByteArrayOutputStream


@Composable
fun AddItemScreen(saveFunction: (item:Item) -> Unit) {
    val itemName = remember { mutableStateOf("") }
    val storeName = remember { mutableStateOf("") }
    val price = remember { mutableStateOf("") }
    var selectedImageUri = remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImagePicker {
                selectedImageUri.value=it
            }
            OutlinedTextField( modifier = Modifier
                .padding(10.dp)
                .graphicsLayer(alpha = 0.7f),
                value = itemName.value,
                onValueChange = { itemName.value = it },
                label = { Text("Enter Item Name") }
            )
            OutlinedTextField( modifier = Modifier
                .padding(10.dp)
                .graphicsLayer(alpha = 0.7f),
                value = storeName.value,
                onValueChange = { storeName.value = it },
                label = { Text("Enter Store Name") }
            )

            OutlinedTextField( modifier = Modifier
                .padding(10.dp)
                .graphicsLayer(alpha = 0.7f),
                value = price.value,
                onValueChange = { price.value = it },
                label = { Text("Enter Price") }
            )


            /* TextField(
                 modifier = Modifier
                     .padding(10.dp)
                     .graphicsLayer(alpha = 0.7f),
                 value = itemName.value,
                 placeholder = { Text("Enter Item Name") },
                 onValueChange = {
                     itemName.value = it

                 })
             TextField(
                 modifier = Modifier
                     .padding(10.dp)
                     .graphicsLayer(alpha = 0.7f),
                 value = storeName.value,
                 placeholder = { Text("Enter Item Store Name") },
                 onValueChange = {
                     storeName.value = it

                 })
             TextField(
                 modifier = Modifier
                     .padding(10.dp)
                     .graphicsLayer(alpha = 0.7f),
                 value = price.value,
                 placeholder = { Text("Enter Item Price") },
                 onValueChange = {
                     price.value = it
                 })

             */
         /*   Button(onClick = {
                var imageByteArray = selectedImageUri.value?.let {uri->
                    resizeImage(context,uri,300,200)
                }?: ByteArray(0)


                val insertItem = Item(itemName.value, storeName.value, price.value, image = imageByteArray)
                saveFunction(insertItem)
            }, modifier = Modifier.padding(10.dp)) {
                Text("Save")
            }

          */
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButtonExample {
                var imageByteArray = selectedImageUri.value?.let {uri->
                    resizeImage(context,uri,300,200)
                }?: ByteArray(0)

                val insertItem = Item(itemName.value, storeName.value, price.value, image = imageByteArray)
                saveFunction(insertItem)

            }

        }


    }

}

@Composable
fun OutlinedButtonExample(onClick: () -> Unit) {
    OutlinedButton(onClick = { onClick() }, modifier = Modifier.size(250.dp,40.dp)) {
        Text("Save")
    }
}


@Composable
fun ImagePicker(onImageSelected: (Uri?) -> Unit) {
    val image = remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES

    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }
    val galleryLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            image.value = uri

        }

    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                galleryLauncher.launch("image/*")
            } else {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            }


        }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (image.value != null) {
            Image(
                painter = rememberAsyncImagePainter(model = image.value),
                contentDescription = "Selected Image",
                modifier = Modifier.width(300.dp).height(200.dp)
            )
            onImageSelected(image.value) // ✅ image.value null değilse çağır.
        } else {
            Image(
                painter = painterResource(R.drawable.download),
                contentDescription = "Default Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(300.dp, 200.dp)
                    .padding(10.dp)
                    .clickable {
                        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                            galleryLauncher.launch("image/*")
                        } else {
                            permissionLauncher.launch(permission)
                        }
                    }
            )
        }
    }

}


fun resizeImage(context : Context, imageUri: Uri,maxWidth:Int,maxHeight:Int):ByteArray?{
    try {
        var inputStream =   context.contentResolver.openInputStream(imageUri)
        var originalBitmap = BitmapFactory.decodeStream(inputStream)
        var ratio = originalBitmap.width.toFloat()/originalBitmap.height.toFloat()
        var width= maxWidth
        var height = (width/ratio).toInt()
        if(height>maxHeight){
            height=maxHeight
            width=(height*ratio).toInt()
        }
        val reziedBitmap = Bitmap.createScaledBitmap(originalBitmap,width,height,false)
        val byteArrayOutputStream = ByteArrayOutputStream()
        reziedBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()

    }catch (e: Exception){
        return null
    }




}