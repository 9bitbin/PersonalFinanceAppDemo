package com.example.personalfinanceappdemo // Package declaration

import com.google.firebase.firestore.FieldValue // Import for FieldValue to store server timestamp in Firestore

// Function to handle user login
fun login(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
    // Attempt to sign in with email and password
    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
        // Check if the sign-in task is successful
        if (task.isSuccessful) {
            // If successful, get the user ID of the currently authenticated user
            val userId = auth.currentUser?.uid ?: return@addOnCompleteListener // Safely handle null user ID

            // Update the "lastLogin" field in Firestore for the authenticated user
            db.collection("Users").document(userId).update("lastLogin", FieldValue.serverTimestamp())

            // Call onSuccess callback when login is successful
            onSuccess()
        } else {
            // If sign-in fails, call onFailure callback with the error message
            onFailure(task.exception?.message ?: "Login Failed") // Provide a default message if error message is null
        }
    }
}
