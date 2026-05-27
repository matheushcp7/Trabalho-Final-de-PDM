package com.example.estudotrabalhofinal.navgation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome_screen")
    object Login : Screen("login_screen")
    object CreateAccount : Screen("create_account_screen")
    object CompleteProfile : Screen("complete_profile_screen")
    object Main : Screen("main_screen") // Rota para as telas pós-login
}