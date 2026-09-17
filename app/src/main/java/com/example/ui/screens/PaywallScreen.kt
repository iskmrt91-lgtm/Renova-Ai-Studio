package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaywallScreen(onPlanSelected: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Planını Seç", color = Navy, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = OffWhite)
            )
        },
        containerColor = OffWhite
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PlanCard(
                title = "Ücretsiz Üyelik",
                price = "₺0",
                features = listOf("Temel raporlama", "Sertifika yok"),
                buttonText = "Seç",
                isHighlighted = false,
                onClick = onPlanSelected
            )
            
            PlanCard(
                title = "Profesyonel",
                price = "₺499 / Ay",
                features = listOf("5 kullanıcı", "Sertifikalı raporlama", "Açık havuz", "API/ERP Entegrasyonu"),
                buttonText = "Seç",
                isHighlighted = true,
                badgeText = "Popüler",
                onClick = onPlanSelected
            )

            PlanCard(
                title = "Kurumsal",
                price = "Özel Fiyatlandırma",
                features = listOf("Sınırsız kullanıcı", "Özel raporlama", "Öncelikli destek"),
                buttonText = "Teklif Al",
                isHighlighted = false,
                onClick = onPlanSelected
            )
        }
    }
}

@Composable
fun PlanCard(
    title: String,
    price: String,
    features: List<String>,
    buttonText: String,
    isHighlighted: Boolean,
    badgeText: String? = null,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isHighlighted) Navy else Color.White
        ),
        border = if (!isHighlighted) BorderStroke(1.dp, Slate200) else null
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            if (badgeText != null) {
                Surface(
                    color = Teal,
                    shape = RoundedCornerShape(percent = 50),
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    Text(
                        text = badgeText,
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }

            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = if (isHighlighted) Color.White else Navy
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = price,
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = if (isHighlighted) Green else Navy,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            features.forEach { feature ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Text(
                        text = "✓",
                        color = if (isHighlighted) Green else Teal,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = feature,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = if (isHighlighted) Color.White else TextDark
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isHighlighted) Green else Navy
                )
            ) {
                Text(buttonText, style = MaterialTheme.typography.labelSmall.copy(color = Color.White, fontWeight = FontWeight.Bold))
            }
        }
    }
}
