package dev.samu.tareas.navigation

sealed class AppScreens(val route: String) {
    // Pantallas
    object CRUDScreen: AppScreens("crud_screen")
    object MapaScreen: AppScreens("mapa_screen")
    object EnergiaEolicaScreen: AppScreens("energia_eolica_screen")
}