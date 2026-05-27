package com.example.estudotrabalhofinal.pages.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Diets : BottomNavItem("diets", Icons.Default.Home, "Home")
    object Progress : BottomNavItem("progress", Icons.Default.AddCircle, "Progress")
    object Analyzer : BottomNavItem("analyzer", Icons.Default.AddCircle, "Diets") // "Diets" no screenshot
    object Settings : BottomNavItem("settings", Icons.Default.Settings, "Settings")
}

@Composable
fun MainScreen() {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf(
        BottomNavItem.Diets,
        BottomNavItem.Progress,
        BottomNavItem.Analyzer,
        BottomNavItem.Settings
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedItem) {
                0 -> PersonalizedDietsScreen()
                1 -> ProgressScreen() // MUDANÇA AQUI
                2 -> FoodAnalyzerScreen()
                3 -> Text("Settings Screen") // Placeholder
            }
        }
    }
}