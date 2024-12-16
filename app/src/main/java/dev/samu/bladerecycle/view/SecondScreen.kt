package dev.samu.bladerecycle.view

import android.graphics.drawable.Drawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.utsman.osmandcompose.DefaultMapProperties
import com.utsman.osmandcompose.Marker
import com.utsman.osmandcompose.OpenStreetMap
import com.utsman.osmandcompose.ZoomButtonVisibility
import com.utsman.osmandcompose.rememberCameraState
import com.utsman.osmandcompose.rememberMarkerState
import dev.samu.bladerecycle.data.AppDatabase
import dev.samu.bladerecycle.data.Bookmark
import dev.samu.bladerecycle.data.BookmarkType
import dev.samu.bladerecycle.viewmodel.BookmarkViewModel
import dev.samu.tareas.navigation.AppScreens
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import dev.samu.bladerecycle.R

@Composable
fun SecondScreen(
    viewModel: BookmarkViewModel,
    database: AppDatabase,
    navController: NavHostController
){
    val bookmarks by viewModel.bookmarks.collectAsState()
    val bookmarkstype by viewModel.bookmarkstype.collectAsState()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    MyMapView(bookmarks, bookmarkstype)

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

@Composable
fun MyMapView(
    bookmarks: List<Bookmark>,
    bookmarkType: List<BookmarkType>,
    modifier: Modifier = Modifier
) {

    val cameraState = rememberCameraState {
        geoPoint = if (bookmarks.isNotEmpty()) {
            GeoPoint(bookmarks[0].coordinatesX, bookmarks[0].coordinatesY)
        } else {
            GeoPoint(28.957627225693827, -13.553854217297525)
        }
        zoom = 17.0
    }

    var mapProperties by remember {
        mutableStateOf(DefaultMapProperties)
    }

    SideEffect {
        mapProperties = mapProperties
            .copy(tileSources = TileSourceFactory.MAPNIK)
            .copy(isEnableRotationGesture = true)
            .copy(zoomButtonVisibility = ZoomButtonVisibility.NEVER)
    }

    OpenStreetMap(
        modifier = modifier.fillMaxSize(),
        cameraState = cameraState,
        properties = mapProperties
    ) {
        bookmarks.forEach { bookmark ->
            val context = LocalContext.current

            var icono by remember { mutableStateOf<Drawable?>(null) }

            when (bookmark.typeId) {
                1 -> icono = ContextCompat.getDrawable(context, R.drawable.restaurante)
                2 -> icono = ContextCompat.getDrawable(context, R.drawable.hotel)
                3 -> icono = ContextCompat.getDrawable(context, R.drawable.museo)
                4 -> icono = ContextCompat.getDrawable(context, R.drawable.farmacia)
            }

            val markerState = rememberMarkerState(
                geoPoint = GeoPoint(bookmark.coordinatesX, bookmark.coordinatesY)
            )

            Marker(
                state = markerState,
                title = bookmark.title,
                icon = icono
            ) {
                Column(
                    modifier = Modifier
                        .size(120.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(7.dp))
                        .padding(5.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = it.title)
                    Text(text = it.snippet, fontSize = 10.sp)
                }
            }
        }
    }
}