package com.example.estudotrabalhofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.activity.compose.setContent
import com.example.estudotrabalhofinal.navgation.AppNavigation
import androidx.activity.compose.setContent
import com.example.estudotrabalhofinal.navgation.AppNavigation
import com.example.estudotrabalhofinal.pages.theme.EstudoTrabalhoFinalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EstudoTrabalhoFinalTheme {
                AppNavigation()
            }
        }
    }
}