package com.example.estudotrabalhofinal.navgation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
//import com.example.estudotrabalhofinal.pages.completeprofile.CompleteProfileViewModel
import com.example.estudotrabalhofinal.pages.main.MainScreen
import com.example.estudotrabalhofinal.pages.welcome.WelcomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Welcome.route) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(navController = navController)
        }
        composable(Screen.CompleteProfile.route) {
            //CompleteProfileViewModel(navController = navController)
        }
        composable(Screen.Main.route) {
            MainScreen() // MainScreen gerencia sua própria navegação interna (tabs)
        }
    }
}