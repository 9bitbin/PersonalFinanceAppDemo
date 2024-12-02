package com.example.personalfinanceappdemo

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun WelcomeGreeting() {
    val userEmail = auth.currentUser?.email ?: "User"
    Text(
        text = "Welcome, $userEmail!",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center // Corrected textAlign usage
    )
}