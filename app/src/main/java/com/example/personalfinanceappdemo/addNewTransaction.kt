package com.example.personalfinanceappdemo

import androidx.navigation.NavController

// Function to add a new transaction to the database for the current user
fun addNewTransaction(amount: Double, category: String, type: String, navController: NavController) {
    // Retrieves the currently authenticated user's ID; exits the function if the user is not logged in
    val userId = auth.currentUser?.uid ?: return

    // Creates a new transaction object with the provided details
    val transaction = Transaction(amount = amount, category = category, type = type)

    // Accesses the "Transactions" sub-collection within the user's document in the "Users" collection
    db.collection("Users").document(userId).collection("Transactions")
        .add(transaction) // Adds the transaction to the database
        .addOnSuccessListener {
            // If the transaction is successfully added, navigate back to the previous screen
            navController.popBackStack()
        }
}
