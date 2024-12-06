package com.example.personalfinanceappdemo

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
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

// Add Transaction Screen Composable Function
@Composable
fun AddTransactionScreen(navController: NavController) {
    // State variables to store input values for amount, category, and transaction type
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("expense") }
    var expanded by remember { mutableStateOf(false) } // Controls if the dropdown for selecting type is shown
    val options = listOf("income", "expense") // List of transaction types (income or expense)

    // Column layout for the screen, center-aligned with padding
    Column(
        modifier = Modifier
            .fillMaxSize() // Make the column take up the entire available space
            .padding(16.dp), // Add padding around the column
        verticalArrangement = Arrangement.Center, // Vertically center the content
        horizontalAlignment = Alignment.CenterHorizontally // Horizontally center the content
    ) {
        // Image at the top of the screen, centered and with padding
        Image(
            painter = painterResource(id = R.drawable.transaction_img), // Replace with your actual image resource ID
            contentDescription = "Add Transaction Image", // Image description for accessibility
            modifier = Modifier
                .size(150.dp) // Set the image size
                .padding(bottom = 16.dp) // Add padding below the image
        )

        // TextField for inputting the transaction amount
        TextField(
            value = amount, // Bind the value to the 'amount' state variable
            onValueChange = { amount = it }, // Update the 'amount' state when user types
            label = { Text("Amount") }, // Label for the TextField
            modifier = Modifier.fillMaxWidth() // Make the TextField fill the width of the screen
        )

        // Spacer between fields for spacing
        Spacer(modifier = Modifier.height(8.dp))

        // TextField for inputting the transaction category
        TextField(
            value = category, // Bind the value to the 'category' state variable
            onValueChange = { category = it }, // Update the 'category' state when user types
            label = { Text("Category") }, // Label for the TextField
            modifier = Modifier.fillMaxWidth() // Make the TextField fill the width of the screen
        )

        // Spacer between fields for spacing
        Spacer(modifier = Modifier.height(8.dp))

        // Read-only TextField to display the selected transaction type (expense or income)
        TextField(
            value = type, // Bind the value to the 'type' state variable
            onValueChange = {}, // No update function as this field is read-only
            label = { Text("Type") }, // Label for the TextField
            readOnly = true, // Set the TextField to be read-only
            modifier = Modifier.fillMaxWidth().clickable { expanded = !expanded } // Toggle the dropdown visibility when clicked
        )

        // Show the dropdown list if the 'expanded' state is true
        if (expanded) {
            // LazyColumn for displaying the list of options (income or expense)
            LazyColumn {
                items(options) { option -> // Iterate over the options list and display each option
                    Text(
                        text = option.replaceFirstChar { it.uppercase() }, // Capitalize the first letter of the option
                        modifier = Modifier
                            .fillMaxWidth() // Make each option fill the width of the screen
                            .clickable {
                                type = option // Set the selected type to the clicked option
                                expanded = false // Close the dropdown list after selection
                            }
                            .padding(8.dp) // Add padding around each option
                    )
                }
            }
        }

        // Spacer between the dropdown and the button
        Spacer(modifier = Modifier.height(16.dp))

        // Button to add the new transaction
        Button(onClick = {
            // Convert the entered amount to a double, defaulting to 0.0 if invalid
            val amountDouble = amount.toDoubleOrNull() ?: 0.0
            // Call the function to add the new transaction
            addNewTransaction(amountDouble, category, type, navController)
        }) {
            // Text displayed inside the button
            Text("Add Transaction")
        }
    }
}

