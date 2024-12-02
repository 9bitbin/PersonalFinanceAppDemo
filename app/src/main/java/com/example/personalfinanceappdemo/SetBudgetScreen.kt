package com.example.personalfinanceappdemo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


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
