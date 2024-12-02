package com.example.personalfinanceappdemo

import androidx.navigation.NavController

fun logout(navController: NavController) {
    auth.signOut() // Log out the user from Firebase
    navController.navigate("welcome") {
        popUpTo("home") { inclusive = true } // Clear the back stack
    }
}
