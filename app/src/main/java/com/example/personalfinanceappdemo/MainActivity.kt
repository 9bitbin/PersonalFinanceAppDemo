package com.example.personalfinanceappdemo


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.personalfinanceappdemo.ui.theme.PersonalFinanceAppDemoTheme
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Locale


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PersonalFinanceAppDemoTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    Navigation()
                }
            }
        }
    }
}

/// Main Navigation Function




// Firebase Instances
val auth: FirebaseAuth = FirebaseAuth.getInstance()
@SuppressLint("StaticFieldLeak")
val db: FirebaseFirestore = FirebaseFirestore.getInstance()

fun Modifier.gradientBackground() = background(
    brush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFBBDEFB),
            Color(0xFF8ECAF6),
            Color(0xFF64B5F6),
        )
    )
)

// Welcome Screen
@Composable
fun WelcomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .gradientBackground()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.welcome_img), // replace with your image name
            contentDescription = "Welcome Image",
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = 16.dp)
        )

        Text(
            text = "Welcome to Personal Finance App",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { navController.navigate("login") },
            modifier = Modifier.fillMaxWidth(0.8f),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("signup") },
            modifier = Modifier.fillMaxWidth(0.8f),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Sign Up")
        }
    }
}

// Sign-Up Screen
@Composable
fun SignUpScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .gradientBackground()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.signup_img), // replace with your image name
            contentDescription = "Sign Up Image",
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = 16.dp)
        )

        IconButton(onClick = { navController.navigate("welcome") }) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back to Welcome")
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(0.9f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(0.9f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                signUp(email, password, {
                    navController.navigate("home")
                }, {
                    errorMessage = it
                })
            },
            modifier = Modifier.fillMaxWidth(0.8f),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Sign Up")
        }
        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
    }
}


// Login Screen
@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.login_img), // replace with your image name
            contentDescription = "Login Image",
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = 16.dp)
        )

        IconButton(onClick = { navController.navigate("welcome") }) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back to Welcome")
        }
        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            login(email, password, {
                navController.navigate("home")
            }, {
                errorMessage = it
            })
        }) {
            Text("Login")
        }
        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
    }
}


// Set Budget Screen
@Composable
fun SetBudgetScreen(navController: NavController) {
    var budget by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.budget_img), // replace with your image name
            contentDescription = "Set Budget Image",
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = 16.dp)
        )

        Text("Set Your Budget", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = budget, onValueChange = { budget = it }, label = { Text("Budget") })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val budgetDouble = budget.toDoubleOrNull()
            if (budgetDouble != null) {
                saveBudget(budgetDouble, navController)
            } else {
                errorMessage = "Please enter a valid number"
            }
        }) {
            Text("Save Budget")
        }
        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val userId = auth.currentUser?.uid
    var transactions by remember { mutableStateOf(listOf<Transaction>()) }
    var budget by remember { mutableFloatStateOf(0.0f) }
    var expenses by remember { mutableFloatStateOf(0.0f) }
    var lastLogin by remember { mutableStateOf<String?>(null) } // State for last login time

    LaunchedEffect(userId) {
        if (userId != null) {
            transactions = fetchTransactions(userId)
            budget = fetchBudget(userId).toFloat()
            expenses = transactions.filter { it.type == "expense" }.sumOf { it.amount }.toFloat()

            // Fetch the last login time from Firestore
            val userDoc = db.collection("Users").document(userId).get().await()
            val timestamp = userDoc.getTimestamp("lastLogin")
            lastLogin = timestamp?.toDate()?.let { date ->
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(date)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Personal Finance App") },
                actions = {
                    // Profile button to navigate to ProfileScreen
                    IconButton(onClick = { navController.navigate("profile") }) {
                        Icon(Icons.Filled.Person, contentDescription = "Profile")
                    }
                    // About button to navigate to AboutScreen
                    IconButton(onClick = { navController.navigate("about") }) {
                        Icon(Icons.Filled.Info, contentDescription = "About")
                    }
                    // Logout button
                    IconButton(onClick = { logout(navController) }) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_transaction") },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Transaction")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .gradientBackground()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WelcomeGreeting() // Welcome message at the top

            // Display last login time if available
            lastLogin?.let {
                Text(
                    text = "Last Login: $it",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
            }

            // Button to navigate to Set Budget screen
            Button(
                onClick = { navController.navigate("set_budget") },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Set Budget")
            }

            Spacer(modifier = Modifier.height(16.dp))

            FinancialSummary(budget = budget, expenses = expenses)

            Spacer(modifier = Modifier.height(16.dp))

            // Button to clear transactions from the screen
            Button(
                onClick = { transactions = emptyList() },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Clear Transactions")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Transactions",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Display list of transactions
            LazyColumn {
                items(transactions) { transaction ->
                    TransactionItem(transaction = transaction)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                }
            }
        }
    }
}






fun logout(navController: NavController) {
    auth.signOut() // Log out the user from Firebase
    navController.navigate("welcome") {
        popUpTo("home") { inclusive = true } // Clear the back stack
    }
}

// Add Transaction Screen
@Composable
fun AddTransactionScreen(navController: NavController) {
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("expense") }
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("income", "expense")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.transaction_img), // replace with your image name
            contentDescription = "Add Transaction Image",
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = 16.dp)
        )

        TextField(value = amount, onValueChange = { amount = it }, label = { Text("Amount") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = category, onValueChange = { category = it }, label = { Text("Category") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = type,
            onValueChange = {},
            label = { Text("Type") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth().clickable { expanded = !expanded }
        )
        if (expanded) {
            LazyColumn {
                items(options) { option ->
                    Text(
                        text = option.replaceFirstChar { it.uppercase() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                type = option
                                expanded = false
                            }
                            .padding(8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val amountDouble = amount.toDoubleOrNull() ?: 0.0
            addNewTransaction(amountDouble, category, type, navController)
        }) {
            Text("Add Transaction")
        }
    }
}

// Transaction Item
@Composable
fun TransactionItem(transaction: Transaction) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .shadow(2.dp, shape = MaterialTheme.shapes.medium),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = transaction.category,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "${transaction.type}: $${transaction.amount}",
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
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

fun addNewTransaction(amount: Double, category: String, type: String, navController: NavController) {
    val userId = auth.currentUser?.uid ?: return
    val transaction = Transaction(amount = amount, category = category, type = type)
    db.collection("Users").document(userId).collection("Transactions")
        .add(transaction)
        .addOnSuccessListener {
            navController.popBackStack() // Navigate back to HomeScreen
        }
}

// Helper functions for budget
suspend fun fetchBudget(userId: String): Double {
    val doc = db.collection("Users").document(userId).get().await()
    return doc.getDouble("budget") ?: 0.0
}

fun saveBudget(budget: Double, navController: NavController) {
    val userId = auth.currentUser?.uid ?: return
    db.collection("Users").document(userId).update("budget", budget)
        .addOnSuccessListener {
            navController.navigate("home")
        }
}

// Helper Data Class
data class Transaction(
    val amount: Double = 0.0,
    val category: String = "",
    val type: String = "",
    val date: Timestamp = Timestamp.now() // Add a default date using Firestore's Timestamp
)


@Composable
fun FinancialSummary(budget: Float, expenses: Float) {
    val remainingBalance = budget - expenses
    val expensesProgress = (expenses / budget).coerceIn(0f, 1f)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Financial Summary",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.onSurfaceVariant)

            Spacer(modifier = Modifier.height(16.dp))

            // Row for Monthly Spending
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Monthly Spending:",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "$${expenses}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Row for Budget
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Budget:",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "$${budget}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Row for Expenses with circular progress indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Expenses:",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircularProgressIndicator(
                        progress = { expensesProgress },
                        modifier = Modifier.size(32.dp),
                        color = MaterialTheme.colorScheme.secondary,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "$${expenses}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Row for Remaining Balance
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Remaining Balance:",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "$${remainingBalance}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50) // Green for positive balance
                )
            }
        }
    }
}

@Composable
fun WelcomeGreeting() {
    val userEmail = auth.currentUser?.email ?: "User"
    Text(
        text = "Welcome, $userEmail!",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center // Corrected textAlign usage
    )
}

@Composable
fun ProfileScreen(navController: NavController) {
    val userEmail = auth.currentUser?.email ?: "User"
    val userId = auth.currentUser?.uid

    Column(
        modifier = Modifier
            .fillMaxSize()
            .gradientBackground()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Image
        Image(
            painter = painterResource(id = R.drawable.profile_img), // Replace with your image resource
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = 16.dp)
        )

        // User Email
        Text(
            text = "Email: $userEmail",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Additional User Info (if any)
        Text(
            text = "User ID: $userId",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button to go back to Home
        Button(
            onClick = { navController.navigate("home") },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Back to Home")
        }
    }
}

@Composable
fun AboutScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .gradientBackground()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "About This App",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Personal Finance App helps you manage your finances, set budgets, and track expenses. " +
                    "This application is designed for personal use and ensures that your data remains secure.\n\n" +
                    "Version: 1.0.0",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.navigate("home") },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Back to Home")
        }
    }
}
// this was the original project code.