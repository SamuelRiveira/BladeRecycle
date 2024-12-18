package dev.samu.tareas.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.samu.bladerecycle.data.AppDatabase
import dev.samu.bladerecycle.view.CRUDScreen
import dev.samu.bladerecycle.view.EnergiaEolicaScreen
import dev.samu.bladerecycle.view.MapaScreen
import dev.samu.bladerecycle.viewmodel.BookmarkViewModel
import dev.samu.bladerecycle.viewmodel.EmpresaViewModel

@Composable
fun AppNavigation(modifier: Modifier, viewModel: BookmarkViewModel, empresaViewModel: EmpresaViewModel, database: AppDatabase){
    // estado de gestion de navegación
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.CRUDScreen.route) {
        composable(route = AppScreens.CRUDScreen.route) {
            Log.d("Prueba", "Lleva a la ruta")
            CRUDScreen(navController = navController, viewModel = viewModel, database = database)
        }
        composable(route = AppScreens.MapaScreen.route) {
            Log.d("Prueba", "Lleva a la ruta")
            MapaScreen(navController = navController, viewModel = viewModel, database = database)
        }
        composable(route = AppScreens.EnergiaEolicaScreen.route) {
            Log.d("Prueba", "Lleva a la ruta")
            EnergiaEolicaScreen(navController = navController)
        }
    }
}
