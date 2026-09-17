package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.example.R
import com.example.ui.theme.*

@Composable
fun SplashScreen(onNavigateToLogin: () -> Unit) {
    LaunchedEffect(key1 = true) {
        delay(1000) // Show for 1s
        onNavigateToLogin()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhite),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            com.example.ui.components.RenovaLogo(modifier = Modifier.width(240.dp))
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Kentsel Dönüşümde Sıfır Atık",
                style = androidx.compose.material3.MaterialTheme.typography.bodyLarge.copy(color = Green, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            )
        }
    }
}
