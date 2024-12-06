// Package declaration for the project
package com.example.personalfinanceappdemo

// Importing necessary Jetpack Compose components and resources
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

// Composable function for the Profile screen
@Composable
fun ProfileScreen(navController: NavController) {
    // Fetching the current user's email and user ID from Firebase Authentication
    val userEmail = auth.currentUser?.email ?: "User" // Default to "User" if email is null
    val userId = auth.currentUser?.uid // Fetch the unique user ID

    // Main layout for the Profile screen
    Column(
        modifier = Modifier
            .fillMaxSize() // Fills the entire screen
            .gradientBackground() // Applies a gradient background (defined elsewhere)
            .padding(16.dp), // Adds padding to the screen edges
        verticalArrangement = Arrangement.Center, // Centers content vertically
        horizontalAlignment = Alignment.CenterHorizontally // Centers content horizontally
    ) {
        // Displaying the profile image
        Image(
            painter = painterResource(id = R.drawable.profile_img), // Loads the profile image resource
            contentDescription = "Profile Image", // Accessibility description for the image
            modifier = Modifier
                .size(150.dp) // Sets the image size
                .padding(bottom = 16.dp) // Adds padding below the image
        )

        // Displaying the user's email
        Text(
            text = "Email: $userEmail", // Dynamically displays the user's email
            style = MaterialTheme.typography.bodyLarge, // Applies a large text style
            fontWeight = FontWeight.Bold, // Makes the text bold
            color = MaterialTheme.colorScheme.primary, // Uses the primary color from the theme
            modifier = Modifier.padding(bottom = 8.dp) // Adds spacing below the email text
        )

        // Displaying the user's ID (optional information)
        Text(
            text = "User ID: $userId", // Dynamically displays the user's ID
            style = MaterialTheme.typography.bodyMedium, // Applies a medium text style
            color = MaterialTheme.colorScheme.onSurfaceVariant, // Uses a variant of the surface color
            modifier = Modifier.padding(bottom = 8.dp) // Adds spacing below the user ID text
        )

        // Spacer for additional vertical spacing
        Spacer(modifier = Modifier.height(16.dp))

        // Button to navigate back to the Home screen
        Button(
            onClick = { navController.navigate("home") }, // Navigates to the "home" screen when clicked
            modifier = Modifier.fillMaxWidth(0.8f) // Sets the button width to 80% of the screen
        ) {
            Text("Back to Home") // Text displayed on the button
        }
    }
}

