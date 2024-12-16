package dev.samu.tareas.navigation

sealed class AppScreens(val route: String) {
    // Pantallas
    object FirstScreen: AppScreens("first_screen")
    object SecondScreen: AppScreens("second_screen")
}