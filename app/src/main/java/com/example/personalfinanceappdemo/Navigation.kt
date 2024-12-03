// Package declaration for the project
package com.example.personalfinanceappdemo

// Importing necessary Jetpack Compose and Navigation components
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Composable function responsible for managing navigation between screens
@Composable
fun Navigation() {
    // Creating a NavController to manage app navigation
    val navController = rememberNavController()

    // NavHost defines the navigation graph for the app
    NavHost(
        navController = navController, // Controller that handles navigation actions
        startDestination = "welcome"  // Initial screen displayed when the app launches
    ) {
        // Defining a composable destination for the "welcome" screen
        composable("welcome") {
            WelcomeScreen(navController) // Invoking the WelcomeScreen Composable and passing the navController
        }
        // Composable destination for the "signup" screen
        composable("signup") {
            SignUpScreen(navController) // Invoking the SignUpScreen Composable
        }
        // Composable destination for the "login" screen
        composable("login") {
            LoginScreen(navController) // Invoking the LoginScreen Composable
        }
        // Composable destination for the "home" screen
        composable("home") {
            HomeScreen(navController) // Invoking the HomeScreen Composable
        }
        // Composable destination for the "add_transaction" screen
        composable("add_transaction") {
            AddTransactionScreen(navController) // Invoking the AddTransactionScreen Composable
        }
        // Composable destination for the "set_budget" screen
        composable("set_budget") {
            SetBudgetScreen(navController) // Invoking the SetBudgetScreen Composable
        }
        // Composable destination for the "profile" screen
        composable("profile") {
            ProfileScreen(navController) // Invoking the ProfileScreen Composable
        }
        // Composable destination for the "about" screen
        composable("about") {
            AboutScreen(navController) // Invoking the AboutScreen Composable
        }
    }
}
