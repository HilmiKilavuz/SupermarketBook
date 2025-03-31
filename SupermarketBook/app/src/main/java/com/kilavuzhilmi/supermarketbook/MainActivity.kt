package com.kilavuzhilmi.supermarketbook

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kilavuzhilmi.supermarketbook.screens.ItemList
import com.kilavuzhilmi.supermarketbook.ui.theme.SupermarketBookTheme
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kilavuzhilmi.supermarketbook.screens.AddItemScreen
import com.kilavuzhilmi.supermarketbook.screens.DetailScreen
import com.kilavuzhilmi.supermarketbook.viewmodel.ItemViewModel


class MainActivity : ComponentActivity() {
    val viewModel : ItemViewModel by viewModels<ItemViewModel>()
    @SuppressLint("RememberReturnType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            SupermarketBookTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavHost(navController, startDestination = "list_Screen"){
                           composable ("list_Screen"){
                               viewModel.getItemList()
                               val itemList by remember {
                                   viewModel.itemList
                               }
                               ItemList(itemList,navController)
                           }
                            composable("add_Item_Screen") {
                                AddItemScreen {
                                    viewModel.saveItem(it)
                                    navController.navigate("list_Screen")
                                }
                            }
                            composable("details_Screen/{itemId}",
                                arguments = listOf(navArgument("itemId") {
                                    type= NavType.StringType
                                })) {
                                val itemString = remember {
                                    it.arguments?.getString("itemId")

                                }

                                viewModel.getItemById(itemString?.toIntOrNull()?:1)
                                val selectedItem by remember {
                                    viewModel.selectedItem
                                }
                                DetailScreen(selectedItem) {
                                    viewModel.deleteItem(selectedItem)
                                    navController.navigate("list_Screen")

                                }



                            }


                        }
                    }

                }
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SupermarketBookTheme {
        AddItemScreen() {}

    }
}