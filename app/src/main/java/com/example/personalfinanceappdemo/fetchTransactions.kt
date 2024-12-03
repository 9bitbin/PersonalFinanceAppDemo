package com.example.personalfinanceappdemo // Package declaration

import kotlinx.coroutines.tasks.await // Importing the 'await' extension function for coroutines

// Helper function to fetch transactions for a user
suspend fun fetchTransactions(userId: String): List<Transaction> {
    return try {
        // Fetch the "Transactions" collection for the specified userId from the Firestore database
        db.collection("Users").document(userId).collection("Transactions")
            .get() // Retrieve all documents from the "Transactions" collection
            .await() // Await the result asynchronously (suspending the coroutine until it's complete)
            .map { it.toObject(Transaction::class.java) } // Convert each document to a Transaction object
    } catch (e: Exception) {
        // In case of an error (e.g., network issue), return an empty list
        emptyList()
    }
}
