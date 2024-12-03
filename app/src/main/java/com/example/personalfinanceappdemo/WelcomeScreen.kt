// Package declaration for the project
package com.example.personalfinanceappdemo

// Import necessary Compose libraries and components for UI
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

// Composable function to display the Welcome Screen
@Composable
fun WelcomeScreen(navController: NavController) {
    // Column layout for arranging UI components vertically
    Column(
        modifier = Modifier
            .fillMaxSize() // Make the column take up the full size of the screen
            .gradientBackground() // Apply a gradient background
            .padding(16.dp), // Apply padding around the content
        verticalArrangement = Arrangement.Center, // Vertically center the components
        horizontalAlignment = Alignment.CenterHorizontally // Horizontally center the components
    ) {
        // Display the welcome image (replace with actual image resource)
        Image(
            painter = painterResource(id = R.drawable.welcome_img), // Load the image resource
            contentDescription = "Welcome Image", // Set content description for accessibility
            modifier = Modifier
                .size(150.dp) // Set the size of the image
                .padding(bottom = 16.dp) // Add padding below the image
        )

        // Display the welcome message as a large headline text
        Text(
            text = "Welcome to Personal Finance App", // The text displayed in the welcome message
            style = MaterialTheme.typography.headlineLarge, // Use the large headline style from the theme
            fontWeight = FontWeight.Bold, // Make the text bold for emphasis
            color = MaterialTheme.colorScheme.primary // Set the text color to the primary color from the theme
        )

        // Spacer to create space between the welcome message and the buttons
        Spacer(modifier = Modifier.height(32.dp))

        // Button for login action
        Button(
            onClick = { navController.navigate("login") }, // Navigate to the login screen when clicked
            modifier = Modifier.fillMaxWidth(0.8f), // Make the button take up 80% of the screen width
            shape = MaterialTheme.shapes.medium // Apply medium-shaped corners to the button
        ) {
            Text("Login") // Display "Login" text inside the button
        }

        // Spacer to create space between the login button and the sign-up button
        Spacer(modifier = Modifier.height(16.dp))

        // Button for sign-up action
        Button(
            onClick = { navController.navigate("signup") }, // Navigate to the sign-up screen when clicked
            modifier = Modifier.fillMaxWidth(0.8f), // Make the button take up 80% of the screen width
            shape = MaterialTheme.shapes.medium // Apply medium-shaped corners to the button
        ) {
            Text("Sign Up") // Display "Sign Up" text inside the button
        }
    }
}
