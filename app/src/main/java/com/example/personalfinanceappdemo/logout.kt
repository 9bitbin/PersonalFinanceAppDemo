package com.example.personalfinanceappdemo // Package declaration

import androidx.navigation.NavController // Import for NavController to manage screen navigation

// Logout function to handle user logout
fun logout(navController: NavController) {
    auth.signOut() // Log out the user from Firebase by calling the signOut method

    // Navigate to the welcome screen and clear the back stack
    navController.navigate("welcome") {
        // Pop up all screens up to and including the "home" screen
        popUpTo("home") { inclusive = true } // This will remove the "home" screen from the back stack
    }
}
