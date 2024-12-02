import com.example.personalfinanceappdemo.db
import kotlinx.coroutines.tasks.await

// Helper functions for budget
suspend fun fetchBudget(userId: String): Double {
    val doc = db.collection("Users").document(userId).get().await()
    return doc.getDouble("budget") ?: 0.0
}
