package dev.samu.bladerecycle.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
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
fun CRUDScreen(
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

    // State for Dialog visibility and editing
    var showDialog by remember { mutableStateOf(false) }
    var showTypeDialog by remember { mutableStateOf(false) }
    var isEditingBookmark by remember { mutableStateOf(false) }
    var isEditingType by remember { mutableStateOf(false) }
    var currentBookmark: Bookmark? by remember { mutableStateOf(null) }
    var currentType: BookmarkType? by remember { mutableStateOf(null) }

    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .verticalScroll(scrollState)
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
                        val type = bookmarkstype.find { it.name == bookmarkTypeName }
                        if (type != null) {
                            val newBookmark = Bookmark(
                                title = bookmarkTitle,
                                coordinatesX = bookmarkX.toDouble(),
                                coordinatesY = bookmarkY.toDouble(),
                                typeId = type.id
                            )
                            viewModel.addBookmark(newBookmark)
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp), // Añadir espacio entre las filas
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Mostrar el título del marcador y sus coordenadas
                    Text(
                        text = "Marcador: ${bookmark.title} (${bookmark.coordinatesX}, ${bookmark.coordinatesY})",
                        modifier = Modifier.weight(1f) // Ocupa el máximo espacio disponible
                    )

                    // Mostrar los iconos de editar y eliminar con un espaciado adecuado
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp) // Añadir espacio entre iconos
                    ) {
                        IconButton(onClick = {
                            // Set current bookmark for editing
                            currentBookmark = bookmark
                            isEditingBookmark = true
                            bookmarkTitle = bookmark.title
                            bookmarkX = bookmark.coordinatesX.toString()
                            bookmarkY = bookmark.coordinatesY.toString()
                            bookmarkTypeName = bookmarkstype.find { it.id == bookmark.typeId }?.name.orEmpty()
                            showDialog = true
                        }) {
                            Icon(Icons.Filled.Edit, contentDescription = "Editar Marcador")
                        }
                        IconButton(onClick = {
                            // Delete the bookmark
                            viewModel.deleteBookmark(bookmark)
                        }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Eliminar Marcador")
                        }
                    }
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            // List of bookmark types
            Text("Lista de Tipos de Marcadores:", style = MaterialTheme.typography.bodyLarge)
            bookmarkstype.forEach { type ->
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Tipo: ${type.name}")
                    Row {
                        IconButton(onClick = {
                            // Set current type for editing
                            currentType = type
                            bookmarkTypeName = type.name
                            isEditingType = true
                            showTypeDialog = true
                        }) {
                            Icon(Icons.Filled.Edit, contentDescription = "Editar Tipo de Marcador")
                        }
                        IconButton(onClick = {
                            viewModel.deleteBookmarkType(type)
                        }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Eliminar Tipo de Marcador")
                        }
                    }
                }
            }
        }

        // Dialog for Editing Bookmark
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Editar Marcador") },
                text = {
                    Column {
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
                            label = { Text("Tipo de Marcador") },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            currentBookmark?.let {
                                val updatedBookmark = it.copy(
                                    title = bookmarkTitle,
                                    coordinatesX = bookmarkX.toDouble(),
                                    coordinatesY = bookmarkY.toDouble(),
                                    typeId = bookmarkstype.find { it.name == bookmarkTypeName }?.id ?: it.typeId
                                )
                                viewModel.updateBookmark(updatedBookmark)
                            }
                            showDialog = false
                        }
                    ) {
                        Text("Guardar Cambios")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { showDialog = false }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }

        // Dialog for Editing Bookmark Type
        if (showTypeDialog) {
            AlertDialog(
                onDismissRequest = { showTypeDialog = false },
                title = { Text("Editar Tipo de Marcador") },
                text = {
                    Column {
                        TextField(
                            value = bookmarkTypeName,
                            onValueChange = { bookmarkTypeName = it },
                            label = { Text("Nombre del Tipo de Marcador") },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            currentType?.let {
                                val updatedType = it.copy(name = bookmarkTypeName)
                                viewModel.updateBookmarkType(updatedType)
                            }
                            showTypeDialog = false
                        }
                    ) {
                        Text("Guardar Cambios")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { showTypeDialog = false }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }

        // BottomAppBar with Navigation
        BottomAppBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(100.dp),
            actions = {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Edit, "Menú", tint = Color.White) },
                    label = { Text("Menú", color = Color.White) },
                    selected = currentRoute == AppScreens.CRUDScreen.route,
                    onClick = {
                        if (currentRoute != AppScreens.CRUDScreen.route) {
                            navController.navigate(AppScreens.CRUDScreen.route)
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.LocationOn, "Mapa", tint = Color.White) },
                    label = { Text("Mapa", color = Color.White) },
                    selected = currentRoute == AppScreens.MapaScreen.route,
                    onClick = {
                        if (currentRoute != AppScreens.MapaScreen.route) {
                            navController.navigate(AppScreens.MapaScreen.route)
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Info, "Info", tint = Color.White) },
                    label = { Text("Info", color = Color.White) },
                    selected = currentRoute == AppScreens.EnergiaEolicaScreen.route,
                    onClick = {
                        if (currentRoute != AppScreens.EnergiaEolicaScreen.route) {
                            navController.navigate(AppScreens.EnergiaEolicaScreen.route)
                        }
                    }
                )
            },
            containerColor = Color(0xff242424)
        )
    }
}
