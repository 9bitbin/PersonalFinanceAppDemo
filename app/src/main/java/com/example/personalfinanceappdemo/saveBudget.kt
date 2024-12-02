package com.example.personalfinanceappdemo

import androidx.navigation.NavController


fun saveBudget(budget: Double, navController: NavController) {
    val userId = auth.currentUser?.uid ?: return
    db.collection("Users").document(userId).update("budget", budget)
        .addOnSuccessListener {
            navController.navigate("home")
        }
}