// Package declaration for the project
package com.example.personalfinanceappdemo

// Import necessary Compose libraries and components for UI
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

// Composable function to display a welcome message to the user
@Composable
fun WelcomeGreeting() {
    // Retrieve the current user's email from Firebase Authentication; fallback to "User" if null
    val userEmail = auth.currentUser?.email ?: "User"

    // Display the welcome message as a text element
    Text(
        text = "Welcome, $userEmail!", // Personalized greeting with user's email
        style = MaterialTheme.typography.titleMedium, // Apply a medium-sized title style from the theme
        fontWeight = FontWeight.Bold, // Use bold font weight for emphasis
        color = MaterialTheme.colorScheme.primary, // Set the text color to the primary color of the theme
        modifier = Modifier
            .padding(bottom = 16.dp) // Add padding below the text for spacing
            .fillMaxWidth(), // Make the text span the full width of its container
        textAlign = TextAlign.Center // Align the text to the center of its container
    )
}
