import com.example.personalfinanceappdemo.db // Importing the database reference
import kotlinx.coroutines.tasks.await // Importing the 'await' extension function for coroutines

// Helper function to fetch the budget for a user
suspend fun fetchBudget(userId: String): Double {
    // Fetch the document for the specified userId from the "Users" collection in the database
    val doc = db.collection("Users").document(userId).get().await()

    // Retrieve the budget value from the document; return 0.0 if it's null
    return doc.getDouble("budget") ?: 0.0
}

