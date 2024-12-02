package com.example.personalfinanceappdemo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import fetchBudget
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Locale


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


