package com.example.personalfinanceappdemo // Package declaration

import androidx.compose.foundation.Image // Import for Image composable
import androidx.compose.foundation.layout.Arrangement // Import for layout arrangement
import androidx.compose.foundation.layout.Column // Import for Column composable
import androidx.compose.foundation.layout.Spacer // Import for Spacer composable
import androidx.compose.foundation.layout.fillMaxSize // Modifier to make the layout fill the maximum size
import androidx.compose.foundation.layout.height // Modifier to set height of elements
import androidx.compose.foundation.layout.padding // Modifier for padding
import androidx.compose.foundation.layout.size // Modifier to set size of elements
import androidx.compose.material.icons.Icons // Import for Icons in Material Design
import androidx.compose.material.icons.automirrored.filled.ArrowBack // Import for back arrow icon
import androidx.compose.material3.Button // Import for Material 3 Button composable
import androidx.compose.material3.Icon // Import for Icon composable
import androidx.compose.material3.IconButton // Import for IconButton composable
import androidx.compose.material3.MaterialTheme // Import for Material theme styling
import androidx.compose.material3.Text // Import for Text composable
import androidx.compose.material3.TextField // Import for TextField composable
import androidx.compose.runtime.Composable // Import for Composable functions
import androidx.compose.runtime.getValue // Import for state handling
import androidx.compose.runtime.mutableStateOf // Import for mutable state
import androidx.compose.runtime.remember // Import for remembering state across recompositions
import androidx.compose.runtime.setValue // Import for setting mutable state
import androidx.compose.ui.Alignment // Import for layout alignment
import androidx.compose.ui.Modifier // Import for Modifier
import androidx.compose.ui.res.painterResource // Import for image resources
import androidx.compose.ui.text.input.PasswordVisualTransformation // Import for visual transformation of passwords
import androidx.compose.ui.unit.dp // Import for dp unit
import androidx.navigation.NavController // Import for NavController to navigate between screens


// Login Screen Composable
@Composable
fun LoginScreen(navController: NavController) {
    // Declare state variables to hold the email, password, and any error message
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Layout container for the login screen
    Column(
        modifier = Modifier
            .fillMaxSize() // Fill the entire screen size
            .padding(16.dp), // Apply padding around the content
        verticalArrangement = Arrangement.Center, // Vertically center the content
        horizontalAlignment = Alignment.CenterHorizontally // Horizontally center the content
    ) {
        // Login Image
        Image(
            painter = painterResource(id = R.drawable.login_img), // Use your own image resource here
            contentDescription = "Login Image", // Accessibility description for the image
            modifier = Modifier
                .size(150.dp) // Set the size of the image
                .padding(bottom = 16.dp) // Add space below the image
        )

        // Back Button that navigates to the Welcome screen
        IconButton(onClick = { navController.navigate("welcome") }) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back to Welcome")
        }

        // TextField for entering email
        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })

        Spacer(modifier = Modifier.height(8.dp)) // Space between the input fields

        // TextField for entering password with visual transformation for security
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation() // Hide password characters
        )

        Spacer(modifier = Modifier.height(8.dp)) // Space between password field and button

        // Login Button that triggers the login process
        Button(onClick = {
            // Call the login function with the email and password, and handle the results
            login(email, password, {
                // On successful login, navigate to the home screen
                navController.navigate("home")
            }, {
                // On failure, set the error message to display
                errorMessage = it
            })
        }) {
            Text("Login") // Button label
        }

        // Display error message if there is any
        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp)) // Space before the error message
            Text(text = it, color = MaterialTheme.colorScheme.error) // Show the error message in red
        }
    }
}

