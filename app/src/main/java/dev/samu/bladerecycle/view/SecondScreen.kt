package dev.samu.bladerecycle.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.samu.bladerecycle.data.AppDatabase
import dev.samu.bladerecycle.viewmodel.BookmarkViewModel
import dev.samu.tareas.navigation.AppScreens

@Composable
fun SecondScreen(
    viewModel: BookmarkViewModel,
    database: AppDatabase,
    navController: NavHostController
){
    Text(text = "Segunda pantalla")

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        BottomAppBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(100.dp),
            actions = {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Edit, "Menú", tint = Color.White) },
                    label = { Text("Menú", color = Color.White) },
                    selected = currentRoute == AppScreens.FirstScreen.route,
                    onClick = {
                        if (currentRoute != AppScreens.FirstScreen.route) {
                            navController.navigate(AppScreens.FirstScreen.route)
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Info, "Mapa", tint = Color.White) },
                    label = { Text("Mapa", color = Color.White) },
                    selected = currentRoute == AppScreens.SecondScreen.route,
                    onClick = {
                        if (currentRoute != AppScreens.SecondScreen.route) {
                            navController.navigate(AppScreens.SecondScreen.route)
                        }
                    }
                )
            },
            containerColor = Color(0xff242424)
        )
    }
}