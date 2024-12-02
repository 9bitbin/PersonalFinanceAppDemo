package com.example.personalfinanceappdemo

import androidx.navigation.NavController


fun addNewTransaction(amount: Double, category: String, type: String, navController: NavController) {
    val userId = auth.currentUser?.uid ?: return
    val transaction = Transaction(amount = amount, category = category, type = type)
    db.collection("Users").document(userId).collection("Transactions")
        .add(transaction)
        .addOnSuccessListener {
            navController.popBackStack() // Navigate back to HomeScreen
        }
}
