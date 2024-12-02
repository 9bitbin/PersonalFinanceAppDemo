package com.example.personalfinanceappdemo

import com.google.firebase.firestore.FieldValue


fun login(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val userId = auth.currentUser?.uid ?: return@addOnCompleteListener
            db.collection("Users").document(userId).update("lastLogin", FieldValue.serverTimestamp())
            onSuccess()
        } else {
            onFailure(task.exception?.message ?: "Login Failed")
        }
    }
}