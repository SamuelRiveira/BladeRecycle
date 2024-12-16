package dev.samu.bladerecycle.view

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.samu.bladerecycle.data.AppDatabase
import dev.samu.bladerecycle.data.Bookmark
import dev.samu.bladerecycle.data.BookmarkType
import dev.samu.bladerecycle.viewmodel.BookmarkViewModel
import dev.samu.tareas.navigation.AppScreens

@Composable
fun FirstScreen(
    database: AppDatabase,
    viewModel: BookmarkViewModel,
    navController: NavHostController
) {
    val bookmarks by viewModel.bookmarks.collectAsState()
    val bookmarkstype by viewModel.bookmarkstype.collectAsState()

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    // State for TextFields
    var bookmarkTitle by remember { mutableStateOf("") }
    var bookmarkX by remember { mutableStateOf("") }
    var bookmarkY by remember { mutableStateOf("") }
    var bookmarkTypeName by remember { mutableStateOf("") }

    val scrollState = rememberScrollState() // Create a scroll state

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .verticalScroll(scrollState) // Apply verticalScroll with the scrollState
                .padding(16.dp)
        ) {
            // Add new Bookmark
            Text("Agregar un Marcador:", style = MaterialTheme.typography.bodyLarge)
            TextField(
                value = bookmarkTitle,
                onValueChange = { bookmarkTitle = it },
                label = { Text("Título del marcador") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            )
            TextField(
                value = bookmarkX,
                onValueChange = { bookmarkX = it },
                label = { Text("Coordenada X") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            )
            TextField(
                value = bookmarkY,
                onValueChange = { bookmarkY = it },
                label = { Text("Coordenada Y") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            )
            TextField(
                value = bookmarkTypeName,
                onValueChange = { bookmarkTypeName = it },
                label = { Text("Nombre del Tipo de Marcador") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            )

            Button(
                onClick = {
                    if (bookmarkTitle.isNotEmpty() && bookmarkX.isNotEmpty() && bookmarkY.isNotEmpty() && bookmarkTypeName.isNotEmpty()) {
                        // Find the bookmark type by name
                        val type = bookmarkstype.find { it.name == bookmarkTypeName }

                        // Add bookmark only if the type exists
                        if (type != null) {
                            val newBookmark = Bookmark(
                                title = bookmarkTitle,
                                coordinatesX = bookmarkX.toDouble(),
                                coordinatesY = bookmarkY.toDouble(),
                                typeId = type.id // Use the ID of the found type
                            )
                            viewModel.addBookmark(newBookmark)
                        } else {
                            // Handle case where type doesn't exist
                            // For example, you can show an error message or create a new type
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text("Agregar Marcador")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Add new Bookmark Type
            Text("Agregar un Tipo de Marcador:", style = MaterialTheme.typography.bodyLarge)
            TextField(
                value = bookmarkTypeName,
                onValueChange = { bookmarkTypeName = it },
                label = { Text("Nombre del Tipo de Marcador") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            )

            Button(
                onClick = {
                    if (bookmarkTypeName.isNotEmpty()) {
                        // Add bookmark type
                        val newType = BookmarkType(name = bookmarkTypeName)
                        viewModel.addBookmarkType(newType)
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text("Agregar Tipo de Marcador")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // List of bookmarks
            Text("Lista de Marcadores:", style = MaterialTheme.typography.bodyLarge)
            bookmarks.forEach { bookmark ->
                Text(text = "Marcador: ${bookmark.title} (${bookmark.coordinatesX}, ${bookmark.coordinatesY})")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // List of bookmark types
            Text("Lista de Tipos de Marcadores:", style = MaterialTheme.typography.bodyLarge)
            bookmarkstype.forEach { type ->
                Text(text = "Tipo: ${type.name}")
            }
        }

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
