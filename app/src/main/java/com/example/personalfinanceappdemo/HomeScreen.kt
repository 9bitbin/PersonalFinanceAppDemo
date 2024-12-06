package com.example.personalfinanceappdemo // Package declaration

import androidx.compose.foundation.layout.Column // Import for vertical layout arrangement
import androidx.compose.foundation.layout.Spacer // Import for adding space between UI components
import androidx.compose.foundation.layout.fillMaxSize // Modifier to make the component take up the full size
import androidx.compose.foundation.layout.fillMaxWidth // Modifier to make the component fill the width
import androidx.compose.foundation.layout.height // Modifier for setting height
import androidx.compose.foundation.layout.padding // Modifier to add padding around elements
import androidx.compose.foundation.lazy.LazyColumn // Import for a scrollable list
import androidx.compose.foundation.lazy.items // Import for iterating through items in LazyColumn
import androidx.compose.material.icons.Icons // Import material icons for UI
import androidx.compose.material.icons.automirrored.filled.ExitToApp // Import icon for exit/logout
import androidx.compose.material.icons.filled.Add // Import icon for adding a transaction
import androidx.compose.material.icons.filled.Info // Import icon for about screen
import androidx.compose.material.icons.filled.Person // Import icon for profile screen
import androidx.compose.material3.Button // Import for Material Button
import androidx.compose.material3.ExperimentalMaterial3Api // For using experimental features of Material 3
import androidx.compose.material3.FloatingActionButton // Import for floating action button
import androidx.compose.material3.HorizontalDivider // Import for horizontal divider line
import androidx.compose.material3.Icon // Import for displaying icons
import androidx.compose.material3.IconButton // Import for icon buttons
import androidx.compose.material3.MaterialTheme // Import for Material Theme components
import androidx.compose.material3.Scaffold // Import for scaffold layout
import androidx.compose.material3.Text // Import for displaying text
import androidx.compose.material3.TopAppBar // Import for top app bar
import androidx.compose.runtime.Composable // Import for composable functions
import androidx.compose.runtime.LaunchedEffect // Import for handling side effects in composables
import androidx.compose.runtime.getValue // Import for getting state values
import androidx.compose.runtime.mutableFloatStateOf // Import for mutable float state
import androidx.compose.runtime.mutableStateOf // Import for mutable state
import androidx.compose.runtime.remember // Import for remembering state across recompositions
import androidx.compose.runtime.setValue // Import for setting state values
import androidx.compose.ui.Alignment // Import for aligning UI components
import androidx.compose.ui.Modifier // Import for applying modifiers to UI components
import androidx.compose.ui.graphics.Color // Import for color manipulation
import androidx.compose.ui.text.font.FontWeight // Import for setting font weight
import androidx.compose.ui.text.style.TextAlign // Import for text alignment styles
import androidx.compose.ui.unit.dp // Import for defining size in dp (density-independent pixels)
import androidx.navigation.NavHostController // Import for navigation control
import fetchBudget // Import for fetching the budget value from the database
import kotlinx.coroutines.tasks.await // Import for async handling in Kotlin
import java.text.SimpleDateFormat // Import for formatting date
import java.util.Locale // Import for locale settings

@OptIn(ExperimentalMaterial3Api::class) // Opt-in annotation for using experimental features in Material 3
@Composable
fun HomeScreen(navController: NavHostController) {
    // Get the user ID of the current authenticated user
    val userId = auth.currentUser?.uid

    // State variables to hold data for transactions, budget, expenses, and last login time
    var transactions by remember { mutableStateOf(listOf<Transaction>()) }
    var budget by remember { mutableFloatStateOf(0.0f) }
    var expenses by remember { mutableFloatStateOf(0.0f) }
    var lastLogin by remember { mutableStateOf<String?>(null) } // State to store last login time

    // Side effect triggered when the userId changes (or when the component is first composed)
    LaunchedEffect(userId) {
        if (userId != null) {
            // Fetch transactions, budget, and expenses based on the user ID
            transactions = fetchTransactions(userId)
            budget = fetchBudget(userId).toFloat()
            expenses = transactions.filter { it.type == "expense" }.sumOf { it.amount }.toFloat()

            // Fetch the last login time from Firestore and format it
            val userDoc = db.collection("Users").document(userId).get().await()
            val timestamp = userDoc.getTimestamp("lastLogin")
            lastLogin = timestamp?.toDate()?.let { date ->
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(date)
            }
        }
    }

    // Scaffold layout that provides structure for the screen
    Scaffold(
        topBar = {
            // TopAppBar with actions (buttons) for profile, about, and logout
            TopAppBar(
                title = { Text("Personal Finance App") },
                actions = {
                    // Profile button to navigate to the Profile screen
                    IconButton(onClick = { navController.navigate("profile") }) {
                        Icon(Icons.Filled.Person, contentDescription = "Profile")
                    }
                    // About button to navigate to the About screen
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
            // Floating action button for adding a new transaction
            FloatingActionButton(
                onClick = { navController.navigate("add_transaction") },
                containerColor = MaterialTheme.colorScheme.primary // Use primary color for the button
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Transaction")
            }
        }
    ) { paddingValues -> // Padding applied to the body of the screen
        Column(
            modifier = Modifier
                .fillMaxSize() // Column fills the entire screen size
                .gradientBackground() // Apply custom gradient background (defined elsewhere)
                .padding(paddingValues) // Apply padding from Scaffold
                .padding(16.dp), // Apply additional padding for layout
            horizontalAlignment = Alignment.CenterHorizontally // Center-align the content horizontally
        ) {
            // Display a greeting message
            WelcomeGreeting()

            // If the last login time is available, display it
            lastLogin?.let {
                Text(
                    text = "Last Login: $it", // Display the formatted last login time
                    style = MaterialTheme.typography.bodySmall, // Small body text style
                    color = Color.Gray, // Set the text color to gray
                    textAlign = TextAlign.Center, // Center-align the text
                    modifier = Modifier
                        .fillMaxWidth() // Ensure the text fills the width
                        .padding(bottom = 8.dp) // Padding below the text
                )
            }

            // Button to navigate to the Set Budget screen
            Button(
                onClick = { navController.navigate("set_budget") },
                modifier = Modifier.fillMaxWidth(0.8f) // Button takes up 80% of the width
            ) {
                Text("Set Budget") // Button text
            }

            Spacer(modifier = Modifier.height(16.dp)) // Spacer to add vertical space

            // Display the financial summary (budget and expenses)
            FinancialSummary(budget = budget, expenses = expenses)

            Spacer(modifier = Modifier.height(16.dp)) // Spacer to add vertical space

            // Button to clear the transactions list from the screen
            Button(
                onClick = { transactions = emptyList() }, // Clear the transactions list
                modifier = Modifier.fillMaxWidth(0.8f) // Button takes up 80% of the width
            ) {
                Text("Clear Transactions") // Button text
            }

            Spacer(modifier = Modifier.height(16.dp)) // Spacer to add vertical space

            // Title text for the "Transactions" section
            Text(
                text = "Transactions",
                style = MaterialTheme.typography.titleSmall, // Small title style
                fontWeight = FontWeight.Bold, // Bold font for title
                modifier = Modifier.padding(vertical = 8.dp) // Vertical padding around the title
            )

            // LazyColumn to display the list of transactions with horizontal dividers between items
            LazyColumn {
                items(transactions) { transaction ->
                    TransactionItem(transaction = transaction) // Display each transaction
                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp)) // Divider between transactions
                }
            }
        }
    }
}

