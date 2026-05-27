package com.example.estudotrabalhofinal.pages.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.estudotrabalhofinal.ui.theme.*


// 1. Defina o esquema de cores usando as variáveis do arquivo Color.kt
private val LightColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    secondary = SecondaryGray,
    background = AppBackground,
    surface = Surface,
    onPrimary = OnPrimary,
    onBackground = OnBackground,
    onSurface = OnSurface
)

// 2. Crie o Composable do Tema
@Composable
fun EstudoTrabalhoFinalTheme(
    content: @Composable () -> Unit
) {
    // Usamos diretamente o LightColorScheme, sem checar o tema do sistema
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography, // Supondo que você tenha um arquivo Type.kt
        content = content
    )
}