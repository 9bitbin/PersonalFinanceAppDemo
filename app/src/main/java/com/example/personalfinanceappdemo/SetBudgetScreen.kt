// Package declaration for the project
package com.example.personalfinanceappdemo

// Importing necessary Jetpack Compose components and navigation library
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

// Composable function for the Set Budget screen
@Composable
fun SetBudgetScreen(navController: NavController) {
    // Mutable state to hold the user's budget input
    var budget by remember { mutableStateOf("") }

    // Mutable state to hold any error message related to input validation
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Layout for the Set Budget screen
    Column(
        modifier = Modifier
            .fillMaxSize() // Fills the entire screen
            .padding(16.dp), // Adds padding around the screen
        verticalArrangement = Arrangement.Center, // Centers content vertically
        horizontalAlignment = Alignment.CenterHorizontally // Centers content horizontally
    ) {
        // Display an image related to budgeting
        Image(
            painter = painterResource(id = R.drawable.budget_img), // Replace with your image resource name
            contentDescription = "Set Budget Image", // Accessibility description for the image
            modifier = Modifier
                .size(150.dp) // Sets the image size
                .padding(bottom = 16.dp) // Adds padding below the image
        )

        // Header text prompting the user to set a budget
        Text(
            "Set Your Budget",
            style = MaterialTheme.typography.headlineMedium // Applies a headline text style
        )

        Spacer(modifier = Modifier.height(16.dp)) // Adds spacing between elements

        // Text field for the user to input their budget
        TextField(
            value = budget, // Binds the state variable to the text field
            onValueChange = { budget = it }, // Updates the state when the input changes
            label = { Text("Budget") } // Label displayed inside the text field
        )

        Spacer(modifier = Modifier.height(16.dp)) // Adds spacing between elements

        // Button to save the budget
        Button(onClick = {
            // Attempt to parse the budget input as a Double
            val budgetDouble = budget.toDoubleOrNull()
            if (budgetDouble != null) {
                saveBudget(budgetDouble, navController) // Save the budget if valid
            } else {
                errorMessage = "Please enter a valid number" // Set an error message if input is invalid
            }
        }) {
            Text("Save Budget") // Button label
        }

        // Display an error message if input validation fails
        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp)) // Adds spacing before the error message
            Text(
                text = it, // The error message text
                color = MaterialTheme.colorScheme.error // Display the error text in the theme's error color
            )
        }
    }
}

