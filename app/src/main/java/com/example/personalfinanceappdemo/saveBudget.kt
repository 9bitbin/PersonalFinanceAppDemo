// Package declaration for the project
package com.example.personalfinanceappdemo

// Importing necessary navigation component
import androidx.navigation.NavController

// Function to save the budget for the current user in the Firestore database
fun saveBudget(budget: Double, navController: NavController) {
    // Get the current user's unique ID from Firebase Authentication
    val userId = auth.currentUser?.uid ?: return // Return early if the user is not logged in

    // Update the "budget" field in the Firestore database for the current user's document
    db.collection("Users").document(userId).update("budget", budget)
        .addOnSuccessListener {
            // If the update is successful, navigate back to the "home" screen
            navController.navigate("home")
        }
}

