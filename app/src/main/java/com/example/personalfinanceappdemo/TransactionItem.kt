// Package declaration for the project
package com.example.personalfinanceappdemo

// Import necessary libraries and components for the UI
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// Composable function to display a single transaction item
@Composable
fun TransactionItem(transaction: Transaction) {
    // Define a Card to visually group and emphasize the transaction item
    Card(
        modifier = Modifier
            .fillMaxWidth() // Make the card span the full width of the parent container
            .padding(vertical = 4.dp) // Add vertical spacing between cards
            .shadow(2.dp, shape = MaterialTheme.shapes.medium), // Apply a shadow for depth
        shape = MaterialTheme.shapes.medium, // Use the medium shape from the theme for rounded corners
        colors = CardDefaults.cardColors(containerColor = Color.White) // Set the background color of the card
    ) {
        // Create a Row layout to display transaction details side by side
        Row(
            modifier = Modifier.padding(12.dp), // Add padding inside the card
            horizontalArrangement = Arrangement.SpaceBetween // Distribute space evenly between child components
        ) {
            // Display the transaction category
            Text(
                text = transaction.category, // Transaction category text
                fontWeight = FontWeight.Medium, // Use medium font weight for emphasis
                color = MaterialTheme.colorScheme.primary // Apply primary color for the text
            )

            // Display the transaction type and amount
            Text(
                text = "${transaction.type}: $${transaction.amount}", // Format text to show type and amount
                fontWeight = FontWeight.Medium, // Use medium font weight for emphasis
                color = MaterialTheme.colorScheme.onSurfaceVariant // Use a secondary color for contrast
            )
        }
    }
}

