package com.example.personalfinanceappdemo // Package declaration

import androidx.compose.foundation.layout.Arrangement // Import layout-related elements for arranging UI components
import androidx.compose.foundation.layout.Column // Import to create vertical layout
import androidx.compose.foundation.layout.Row // Import to create horizontal layout
import androidx.compose.foundation.layout.Spacer // Import to create space between elements
import androidx.compose.foundation.layout.fillMaxWidth // Modifier to make an element fill the width
import androidx.compose.foundation.layout.height // Modifier for height adjustments
import androidx.compose.foundation.layout.padding // Modifier for padding around elements
import androidx.compose.foundation.layout.size // Modifier for setting size of elements
import androidx.compose.foundation.layout.width // Modifier for width adjustments
import androidx.compose.material3.Card // Import the Card component for displaying content
import androidx.compose.material3.CardDefaults // Import for default card styling
import androidx.compose.material3.CircularProgressIndicator // Import CircularProgressIndicator for progress display
import androidx.compose.material3.HorizontalDivider // Import HorizontalDivider for separating content
import androidx.compose.material3.MaterialTheme // Import for accessing Material theme styles
import androidx.compose.material3.Text // Import Text component for displaying text
import androidx.compose.runtime.Composable // Import for making the function composable
import androidx.compose.ui.Alignment // Import for aligning content in layouts
import androidx.compose.ui.Modifier // Import to modify UI elements
import androidx.compose.ui.graphics.Color // Import to work with colors
import androidx.compose.ui.text.font.FontWeight // Import for font weight styling
import androidx.compose.ui.unit.dp // Import for setting size in dp (density-independent pixels)

@Composable
fun FinancialSummary(budget: Float, expenses: Float) {
    // Calculate the remaining balance by subtracting expenses from the budget
    val remainingBalance = budget - expenses

    // Calculate the progress of expenses as a percentage of the budget
    val expensesProgress = (expenses / budget).coerceIn(0f, 1f) // Ensure value is between 0 and 1

    // Card component to display the financial summary with padding and elevation
    Card(
        modifier = Modifier
            .fillMaxWidth() // Card fills the width of the parent
            .padding(16.dp), // Add padding around the card
        colors = CardDefaults.cardColors(containerColor = Color.White), // Set card background color to white
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Set elevation (shadow) for the card
    ) {
        // Column to arrange the content vertically within the card
        Column(
            modifier = Modifier
                .fillMaxWidth() // Column fills the width of the parent card
                .padding(16.dp), // Add padding around the content
            horizontalAlignment = Alignment.CenterHorizontally // Center-align the content horizontally
        ) {
            // Title text for the financial summary
            Text(
                text = "Financial Summary", // Title text
                style = MaterialTheme.typography.headlineSmall, // Use material theme for small headline style
                fontWeight = FontWeight.Bold, // Bold font for title
                color = MaterialTheme.colorScheme.primary, // Primary color for text
                modifier = Modifier.padding(bottom = 8.dp) // Add space below the title
            )

            // Horizontal line to separate the title from the content
            HorizontalDivider(color = MaterialTheme.colorScheme.onSurfaceVariant)

            Spacer(modifier = Modifier.height(16.dp)) // Add space between sections

            // Row to display monthly spending
            Row(
                modifier = Modifier.fillMaxWidth(), // Row fills the width of the parent
                horizontalArrangement = Arrangement.SpaceBetween, // Distribute items with space in between
                verticalAlignment = Alignment.CenterVertically // Vertically center-align the items
            ) {
                // Text for "Monthly Spending"
                Text(
                    text = "Monthly Spending:",
                    style = MaterialTheme.typography.titleSmall, // Small title style
                    color = MaterialTheme.colorScheme.primary // Use primary color for text
                )
                // Text for the actual monthly spending amount
                Text(
                    text = "$${expenses}",
                    style = MaterialTheme.typography.titleMedium, // Medium title style
                    fontWeight = FontWeight.Bold, // Bold font weight
                    color = MaterialTheme.colorScheme.secondary // Secondary color for text
                )
            }

            Spacer(modifier = Modifier.height(8.dp)) // Add space between rows

            // Row to display budget
            Row(
                modifier = Modifier.fillMaxWidth(), // Row fills the width of the parent
                horizontalArrangement = Arrangement.SpaceBetween, // Distribute items with space in between
                verticalAlignment = Alignment.CenterVertically // Vertically center-align the items
            ) {
                // Text for "Budget"
                Text(
                    text = "Budget:",
                    style = MaterialTheme.typography.titleSmall, // Small title style
                    color = MaterialTheme.colorScheme.primary // Use primary color for text
                )
                // Text for the actual budget amount
                Text(
                    text = "$${budget}",
                    style = MaterialTheme.typography.titleMedium, // Medium title style
                    fontWeight = FontWeight.SemiBold, // Semi-bold font weight
                    color = MaterialTheme.colorScheme.primary // Use primary color for text
                )
            }

            Spacer(modifier = Modifier.height(8.dp)) // Add space between rows

            // Row to display expenses with a progress indicator
            Row(
                modifier = Modifier.fillMaxWidth(), // Row fills the width of the parent
                horizontalArrangement = Arrangement.SpaceBetween, // Distribute items with space in between
                verticalAlignment = Alignment.CenterVertically // Vertically center-align the items
            ) {
                // Text for "Expenses"
                Text(
                    text = "Expenses:",
                    style = MaterialTheme.typography.titleSmall, // Small title style
                    color = MaterialTheme.colorScheme.primary // Use primary color for text
                )
                // Row to display the circular progress indicator and the expenses amount
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircularProgressIndicator(
                        progress = expensesProgress, // Use the calculated progress value
                        modifier = Modifier.size(32.dp), // Set size of the circular indicator
                        color = MaterialTheme.colorScheme.secondary, // Secondary color for the progress indicator
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Add space between the progress indicator and the text
                    // Text for the actual expenses amount
                    Text(
                        text = "$${expenses}",
                        style = MaterialTheme.typography.titleMedium, // Medium title style
                        fontWeight = FontWeight.Medium // Medium font weight
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp)) // Add space between rows

            // Row to display the remaining balance
            Row(
                modifier = Modifier.fillMaxWidth(), // Row fills the width of the parent
                horizontalArrangement = Arrangement.SpaceBetween, // Distribute items with space in between
                verticalAlignment = Alignment.CenterVertically // Vertically center-align the items
            ) {
                // Text for "Remaining Balance"
                Text(
                    text = "Remaining Balance:",
                    style = MaterialTheme.typography.titleSmall, // Small title style
                    color = MaterialTheme.colorScheme.primary // Use primary color for text
                )
                // Text for the actual remaining balance
                Text(
                    text = "$${remainingBalance}",
                    style = MaterialTheme.typography.titleMedium, // Medium title style
                    fontWeight = FontWeight.Bold, // Bold font weight
                    color = Color(0xFF4CAF50) // Green color for positive balance
                )
            }
        }
    }
}
