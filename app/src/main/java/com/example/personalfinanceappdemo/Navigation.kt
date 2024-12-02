package com.example.personalfinanceappdemo

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") { WelcomeScreen(navController) }
        composable("signup") { SignUpScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("add_transaction") { AddTransactionScreen(navController) }
        composable("set_budget") { SetBudgetScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("about") { AboutScreen(navController) }
    }
}

