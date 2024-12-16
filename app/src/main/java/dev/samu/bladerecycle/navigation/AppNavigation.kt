package dev.samu.tareas.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.samu.bladerecycle.data.AppDatabase
import dev.samu.bladerecycle.view.FirstScreen
import dev.samu.bladerecycle.view.SecondScreen
import dev.samu.bladerecycle.viewmodel.BookmarkViewModel
@Composable
fun AppNavigation(modifier: Modifier, viewModel: BookmarkViewModel, database: AppDatabase){
    // estado de gestion de navegación
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.FirstScreen.route) {
        composable(route = AppScreens.FirstScreen.route) {
            Log.d("Prueba", "Lleva a la ruta")
            FirstScreen(navController = navController, viewModel = viewModel, database = database)
        }
        composable(route = AppScreens.SecondScreen.route) {
            Log.d("Prueba", "Lleva a la ruta")
            SecondScreen(navController = navController, viewModel = viewModel, database = database)
        }
    }
}
