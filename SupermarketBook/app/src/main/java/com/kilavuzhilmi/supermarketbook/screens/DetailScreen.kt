package com.kilavuzhilmi.supermarketbook.screens

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kilavuzhilmi.supermarketbook.R
import com.kilavuzhilmi.supermarketbook.model.Item

@Composable
fun DetailScreen(item: Item?, deleteFunction: () -> Unit){
    Box(modifier = Modifier.fillMaxSize().
    background(color = MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = item?.itemname ?:"" ,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.padding(2.dp),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center)
            val imageBitmap = item?.image?.let {
                BitmapFactory.decodeByteArray(it,0,it.size).asImageBitmap()
            }
            Image(bitmap = imageBitmap?: ImageBitmap.imageResource(id=R.drawable.download),
                contentDescription = item?.itemname, modifier = Modifier.padding(10.dp).size(300.dp,200.dp))

            Text(text = item?.storename.toString(),
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(2.dp),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center)
            Text(text = item?.price.toString(),
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(2.dp),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center)
            Button(onClick = deleteFunction) {
                Text("Delete")
            }
        }

    }

}