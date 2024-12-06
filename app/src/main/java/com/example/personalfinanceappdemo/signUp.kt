// Package declaration for the project
package com.example.personalfinanceappdemo

// Helper Functions

/**
 * Function to handle user sign-up using Firebase Authentication.
 *
 * @param email The email address entered by the user.
 * @param password The password entered by the user.
 * @param onSuccess Callback function to execute when sign-up is successful.
 * @param onFailure Callback function to execute when sign-up fails, passing an error message.
 */
fun signUp(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
    // Use Firebase Authentication to create a new user with email and password
    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            // If sign-up is successful, get the user's unique ID
            val userId = auth.currentUser?.uid ?: return@addOnCompleteListener

            // Reference to the user's document in the Firestore "Users" collection
            val userDoc = db.collection("Users").document(userId)

            // Initialize the user document with a default budget of 0.0
            userDoc.set(mapOf("budget" to 0.0))
                .addOnSuccessListener {
                    // Call the success callback if the user document is successfully created
                    onSuccess()
                }
                .addOnFailureListener {
                    // Call the failure callback with a custom error message if document setup fails
                    onFailure("Failed to set up user document")
                }
        } else {
            // Call the failure callback with the error message if sign-up fails
            onFailure(task.exception?.message ?: "Sign-Up Failed")
        }
    }
}

