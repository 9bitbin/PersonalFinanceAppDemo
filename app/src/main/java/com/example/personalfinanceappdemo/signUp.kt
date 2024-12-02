package com.example.personalfinanceappdemo


// Helper Functions
fun signUp(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val userId = auth.currentUser?.uid ?: return@addOnCompleteListener
            val userDoc = db.collection("Users").document(userId)

            // Initialize user document with a default budget of 0.0
            userDoc.set(mapOf("budget" to 0.0))
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { onFailure("Failed to set up user document") }
        } else {
            onFailure(task.exception?.message ?: "Sign-Up Failed")
        }
    }
}