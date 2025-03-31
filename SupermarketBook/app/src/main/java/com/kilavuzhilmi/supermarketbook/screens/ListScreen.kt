package com.kilavuzhilmi.supermarketbook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kilavuzhilmi.supermarketbook.model.Item


@Composable
fun ItemRow(item: Item,navController: NavController){
    Spacer(modifier = Modifier.height(16.dp))
    Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.fillMaxWidth().
    background(MaterialTheme.colorScheme.primaryContainer).
    clickable(){
        navController.navigate("details_Screen/${item.id}")
    }) {
        Text(text = item.itemname, modifier = Modifier.padding(2.dp), style = MaterialTheme.typography.displayMedium, fontWeight = FontWeight.Bold)

    }

}

@Composable
fun ItemRow2(item: Item, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                navController.navigate("details_Screen/${item.id}")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.itemname,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Eğer item'da başka gösterilecek bilgiler varsa

            }

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Navigate to details",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun ItemList(itemList: List<Item>,navController: NavController){
    Scaffold(topBar = {}, floatingActionButtonPosition = FabPosition.End, floatingActionButton = {
        FAB {
            navController.navigate("add_Item_Screen")
        }
    }, content = {innerPadding ->
        LazyColumn(contentPadding = innerPadding, modifier = Modifier.background(Color(0x48A6A7)).padding(10.dp).fillMaxSize())
        {
            items(itemList) {
                //ItemRow(it, navController = navController)
                ItemRow2(it, navController = navController)
            }

        }

    })

}
@Composable
fun FAB(onClick:()-> Unit){
    FloatingActionButton(onClick = onClick) {
        Icon(Icons.Filled.Add,"Floating action button.")


    }
}
