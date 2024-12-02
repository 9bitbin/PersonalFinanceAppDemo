package com.example.personalfinanceappdemo

import kotlinx.coroutines.tasks.await


suspend fun fetchTransactions(userId: String): List<Transaction> {
    return try {
        db.collection("Users").document(userId).collection("Transactions")
            .get()
            .await()
            .map { it.toObject(Transaction::class.java) }
    } catch (e: Exception) {
        emptyList()
    }
}