package com.example.personalfinanceappdemo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

// Composable function to define the "About" screen in the app
@Composable
fun AboutScreen(navController: NavController) {
    // Arranging elements in a column layout
    Column(
        modifier = Modifier
            .fillMaxSize() // Makes the column fill the entire screen
            .gradientBackground() // Applies a gradient background (assumes custom extension)
            .padding(16.dp), // Adds padding around the column
        verticalArrangement = Arrangement.Center, // Centers children vertically
        horizontalAlignment = Alignment.CenterHorizontally // Centers children horizontally
    ) {
        // Header text for the screen
        Text(
            text = "About This App",
            style = MaterialTheme.typography.headlineLarge, // Uses a large headline typography
            fontWeight = FontWeight.Bold, // Makes the text bold
            color = MaterialTheme.colorScheme.primary, // Uses primary color for the text
            modifier = Modifier.padding(bottom = 16.dp) // Adds spacing below the text
        )

        // Descriptive text explaining the purpose of the app
        Text(
            text = "Personal Finance App helps you manage your finances, set budgets, and track expenses. " +
                    "This application is designed for personal use and ensures that your data remains secure.\n\n" +
                    "Version: 1.0.0",
            style = MaterialTheme.typography.bodyLarge, // Uses body text typography
            color = MaterialTheme.colorScheme.onSurfaceVariant, // Uses a variant color for text visibility
            textAlign = TextAlign.Center, // Centers the text horizontally
            modifier = Modifier.padding(horizontal = 16.dp) // Adds horizontal padding to the text
        )

        // Spacer to create vertical space between text and button
        Spacer(modifier = Modifier.height(24.dp))

        // Button to navigate back to the Home screen
        Button(
            onClick = { navController.navigate("home") }, // Navigates to the "home" route
            modifier = Modifier.fillMaxWidth(0.8f) // Makes the button 80% of the screen width
        ) {
            Text("Back to Home") // Label displayed on the button
        }
    }
}
