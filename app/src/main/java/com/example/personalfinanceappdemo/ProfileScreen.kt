package com.example.personalfinanceappdemo

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


@Composable
fun ProfileScreen(navController: NavController) {
    val userEmail = auth.currentUser?.email ?: "User"
    val userId = auth.currentUser?.uid

    Column(
        modifier = Modifier
            .fillMaxSize()
            .gradientBackground()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Image
        Image(
            painter = painterResource(id = R.drawable.profile_img), // Replace with your image resource
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = 16.dp)
        )

        // User Email
        Text(
            text = "Email: $userEmail",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Additional User Info (if any)
        Text(
            text = "User ID: $userId",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button to go back to Home
        Button(
            onClick = { navController.navigate("home") },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Back to Home")
        }
    }
}