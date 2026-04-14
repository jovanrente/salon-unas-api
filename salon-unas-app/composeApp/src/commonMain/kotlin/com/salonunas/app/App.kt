package com.salonunas.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.salonunas.app.ui.screens.ClientesScreen
import com.salonunas.app.ui.screens.ManicuristasScreen
import com.salonunas.app.ui.screens.ServiciosScreen
import com.salonunas.app.ui.theme.SalonTheme

private enum class Tab(val title: String, val icon: ImageVector) {
    Clientes("Clientes", Icons.Default.Person),
    Servicios("Servicios", Icons.Default.Star),
    Manicuristas("Manicuristas", Icons.Default.Build)
}

@Composable
fun App() {
    SalonTheme {
        var selected by remember { mutableStateOf(Tab.Clientes) }
        Scaffold(
            bottomBar = {
                NavigationBar {
                    Tab.entries.forEach { tab ->
                        NavigationBarItem(
                            selected = selected == tab,
                            onClick = { selected = tab },
                            icon = { Icon(tab.icon, contentDescription = tab.title) },
                            label = { Text(tab.title) }
                        )
                    }
                }
            }
        ) { padding ->
            Box(Modifier.padding(padding)) {
                when (selected) {
                    Tab.Clientes -> ClientesScreen()
                    Tab.Servicios -> ServiciosScreen()
                    Tab.Manicuristas -> ManicuristasScreen()
                }
            }
        }
    }
}
