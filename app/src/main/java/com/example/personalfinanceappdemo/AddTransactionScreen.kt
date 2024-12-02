package com.example.personalfinanceappdemo

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


// Add Transaction Screen
@Composable
fun AddTransactionScreen(navController: NavController) {
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("expense") }
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("income", "expense")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.transaction_img), // replace with your image name
            contentDescription = "Add Transaction Image",
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = 16.dp)
        )

        TextField(value = amount, onValueChange = { amount = it }, label = { Text("Amount") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = category, onValueChange = { category = it }, label = { Text("Category") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = type,
            onValueChange = {},
            label = { Text("Type") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth().clickable { expanded = !expanded }
        )
        if (expanded) {
            LazyColumn {
                items(options) { option ->
                    Text(
                        text = option.replaceFirstChar { it.uppercase() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                type = option
                                expanded = false
                            }
                            .padding(8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val amountDouble = amount.toDoubleOrNull() ?: 0.0
            addNewTransaction(amountDouble, category, type, navController)
        }) {
            Text("Add Transaction")
        }
    }
}