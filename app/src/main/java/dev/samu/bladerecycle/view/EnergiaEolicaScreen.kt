package dev.samu.bladerecycle.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.samu.bladerecycle.R
import dev.samu.tareas.navigation.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnergiaEolicaScreen(
    navController: NavHostController
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Energía Eólica Sostenible",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()){
            LazyColumn(
                modifier = Modifier.padding(paddingValues).padding(bottom = 100.dp)
            ) {
                item { WelcomeHeader() }
                item { StatisticsSection() }
                item { InfoCard(recyclingInfo, MaterialTheme.colorScheme.primaryContainer) }
                item { InfoCard(companyInfo, MaterialTheme.colorScheme.secondaryContainer) }
                item { InfoCard(maintenanceInfo, MaterialTheme.colorScheme.tertiaryContainer) }
                item { SustainabilitySection() }
                item { ImageGallerySection() }
                item { ContactSection() }
            }
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
}

@Composable
private fun WelcomeHeader() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.wind_turbine_1),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Innovación en Energía Eólica",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Liderando el camino hacia un futuro energético más sostenible",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
private fun StatisticsSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Impacto en Números",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatisticItem("500+", "Turbinas\nMantenidas")
                StatisticItem("95%", "Eficiencia\nMejorada")
                StatisticItem("20+", "Años de\nExperiencia")
            }
        }
    }
}

@Composable
private fun StatisticItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun InfoCard(info: InfoSection, backgroundColor: androidx.compose.ui.graphics.Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    painter = painterResource(id = info.icon),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = info.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = info.content,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun SustainabilitySection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Compromiso con la Sostenibilidad",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = """
                    Nuestro compromiso con el medio ambiente va más allá del reciclaje:
                    
                    • Reducción de la huella de carbono en nuestras operaciones
                    • Uso de vehículos eléctricos para el transporte
                    • Implementación de tecnologías de bajo impacto
                    • Colaboración con centros de investigación
                    • Desarrollo de nuevas técnicas de reciclaje
                    • Educación y concienciación ambiental
                """.trimIndent(),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun ImageGallerySection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Galería de Imágenes",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(galleryImages) { imageRes ->
                Card {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = null,
                        modifier = Modifier
                            .width(280.dp)
                            .height(180.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

@Composable
private fun ContactSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "¿Deseas apuntarte a nuestro plan?",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "¡Registra tu empresa y contactaremos contigo!",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Text("Registrate ahora")
            }
        }
    }
}

private data class InfoSection(
    val title: String,
    val content: String,
    val icon: Int
)

private val recyclingInfo = InfoSection(
    title = "Importancia del Reciclaje",
    content = """
        El reciclaje de palas de aerogeneradores es un desafío crucial para la industria eólica:
        
        • Materiales compuestos de difícil reciclaje (fibra de vidrio y resinas)
        • 2.5 millones de toneladas de materiales compuestos para 2050
        • Necesidad de soluciones innovadoras de reciclaje
        • Desarrollo de nuevos materiales biodegradables
        • Implementación de procesos de economía circular
        • Reducción del impacto ambiental a largo plazo
        • Cumplimiento de normativas ambientales
    """.trimIndent(),
    icon = R.drawable.hotel
)

private val companyInfo = InfoSection(
    title = "Servicios Especializados",
    content = """
        Ofrecemos soluciones integrales para el mantenimiento de aerogeneradores:
        
        • Inspecciones avanzadas con drones y tecnología térmica
        • Análisis predictivo mediante IA y machine learning
        • Reparaciones especializadas in situ
        • Optimización del rendimiento
        • Monitorización en tiempo real
        • Certificación de calidad ISO 9001
        • Equipo técnico altamente cualificado
        • Disponibilidad 24/7
    """.trimIndent(),
    icon = R.drawable.restaurante
)

private val maintenanceInfo = InfoSection(
    title = "Plan de Mantenimiento Integral",
    content = """
        Nuestro plan de mantenimiento garantiza la máxima eficiencia:
        
        • Inspecciones periódicas programadas
        • Análisis estructural mediante ultrasonidos
        • Sistema de monitorización continua
        • Mantenimiento predictivo y preventivo
        • Reparaciones certificadas
        • Informes técnicos detallados
        • Optimización del ciclo de vida
        • Gestión de repuestos
        • Formación continua del personal
    """.trimIndent(),
    icon = R.drawable.ic_launcher_foreground
)

private val galleryImages = listOf(
    R.drawable.wind_turbine_1,
    R.drawable.wind_turbine_2,
    R.drawable.wind_turbine_3,
    R.drawable.maintenance_1
)