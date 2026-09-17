package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(
        containerColor = OffWhite
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .imePadding()
                .padding(24.dp)
                .verticalScroll(androidx.compose.foundation.rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            com.example.ui.components.RenovaLogo(modifier = Modifier.width(280.dp))
            Spacer(modifier = Modifier.height(32.dp))
            
            Text(
                text = "Giriş Yap",
                style = MaterialTheme.typography.headlineMedium.copy(color = Navy)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Sıfır atık hedefine katılın",
                style = MaterialTheme.typography.bodyMedium.copy(color = TextLight)
            )
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-posta veya Telefon") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Navy,
                    unfocusedTextColor = Navy,
                    focusedBorderColor = Navy,
                    unfocusedBorderColor = Slate200
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Şifre") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Navy,
                    unfocusedTextColor = Navy,
                    focusedBorderColor = Navy,
                    unfocusedBorderColor = Slate200
                )
            )
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onLoginSuccess,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Green)
            ) {
                Text("Giriş Yap", style = MaterialTheme.typography.bodyLarge.copy(color = Color.White, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold))
            }
            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { /* TODO */ }) {
                Text("Hesap Oluştur", color = Navy)
            }
        }
    }
}
