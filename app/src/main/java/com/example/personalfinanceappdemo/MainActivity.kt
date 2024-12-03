// Package declaration for the project
package com.example.personalfinanceappdemo

// Importing necessary Android and Jetpack Compose components
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.personalfinanceappdemo.ui.theme.PersonalFinanceAppDemoTheme
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Locale

// Main activity of the app, serves as the entry point
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Setting the content view to use Jetpack Compose
        setContent {
            // Applying the custom app theme
            PersonalFinanceAppDemoTheme {
                // Creating a surface with the theme's background color
                Surface(color = MaterialTheme.colorScheme.background) {
                    // Invoking the navigation function to handle screen navigation
                    Navigation()
                }
            }
        }
    }
}

// Global Firebase Authentication instance for user management
val auth: FirebaseAuth = FirebaseAuth.getInstance()

// Global Firebase Firestore instance for database interactions
@SuppressLint("StaticFieldLeak") // Suppress lint warning for the static Firebase Firestore instance
val db: FirebaseFirestore = FirebaseFirestore.getInstance()

// Extension function to add a vertical gradient background to a Composable
fun Modifier.gradientBackground() = background(
    brush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFBBDEFB), // Light blue
            Color(0xFF8ECAF6), // Medium blue
            Color(0xFF64B5F6), // Dark blue
        )
    )
)

// Data class to represent a financial transaction
data class Transaction(
    val amount: Double = 0.0, // The amount of the transaction (default is 0.0)
    val category: String = "", // The category of the transaction (e.g., Food, Rent)
    val type: String = "", // The type of the transaction (e.g., Income, Expense)
    val date: Timestamp = Timestamp.now() // The date of the transaction (default is the current time)
)
