// Package declaration for the project
package com.example.personalfinanceappdemo

// Import necessary libraries and components for the Sign-Up Screen UI
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

// Composable function for the Sign-Up Screen
@Composable
fun SignUpScreen(navController: NavController) {
    // State variables to store user input for email and password
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // State variable to display error messages, if any
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // UI layout of the Sign-Up screen
    Column(
        modifier = Modifier
            .fillMaxSize() // Make the column occupy the entire screen
            .gradientBackground() // Apply a custom gradient background
            .padding(16.dp), // Add padding around the column
        verticalArrangement = Arrangement.Center, // Align children vertically at the center
        horizontalAlignment = Alignment.CenterHorizontally // Align children horizontally at the center
    ) {
        // Display an image at the top of the screen
        Image(
            painter = painterResource(id = R.drawable.signup_img), // Replace with your image resource
            contentDescription = "Sign Up Image", // Accessibility description for the image
            modifier = Modifier
                .size(150.dp) // Set the size of the image
                .padding(bottom = 16.dp) // Add spacing below the image
        )

        // Back button to navigate to the Welcome screen
        IconButton(onClick = { navController.navigate("welcome") }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack, // Use a back arrow icon
                contentDescription = "Back to Welcome" // Accessibility description for the icon
            )
        }
        Spacer(modifier = Modifier.height(8.dp)) // Add spacing between elements

        // Text field for the user to input their email
        TextField(
            value = email, // Bind the current email value to the text field
            onValueChange = { email = it }, // Update the email value when user types
            label = { Text("Email") }, // Placeholder label for the text field
            modifier = Modifier.fillMaxWidth(0.9f) // Set the width to 90% of the parent
        )
        Spacer(modifier = Modifier.height(8.dp)) // Add spacing between elements

        // Text field for the user to input their password
        TextField(
            value = password, // Bind the current password value to the text field
            onValueChange = { password = it }, // Update the password value when user types
            label = { Text("Password") }, // Placeholder label for the text field
            visualTransformation = PasswordVisualTransformation(), // Mask the password input
            modifier = Modifier.fillMaxWidth(0.9f) // Set the width to 90% of the parent
        )
        Spacer(modifier = Modifier.height(16.dp)) // Add spacing between elements

        // Button to trigger the sign-up process
        Button(
            onClick = {
                // Attempt to sign up the user using the provided email and password
                signUp(email, password, {
                    // Navigate to the Home screen on successful sign-up
                    navController.navigate("home")
                }, {
                    // Display an error message on failure
                    errorMessage = it
                })
            },
            modifier = Modifier.fillMaxWidth(0.8f), // Set the width to 80% of the parent
            shape = MaterialTheme.shapes.medium // Use the medium shape from the theme
        ) {
            Text("Sign Up") // Button text
        }

        // Display error messages if any
        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp)) // Add spacing before the error message
            Text(
                text = it, // Error message text
                color = MaterialTheme.colorScheme.error // Set the text color to the error color
            )
        }
    }
}
