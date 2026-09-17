package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CertificateScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sertifika", color = Navy, fontWeight = FontWeight.Bold) },
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Certificate Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, Green, RoundedCornerShape(24.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    com.example.ui.components.RenovaLogo(modifier = Modifier.width(180.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Sıfır Atık Sertifikası",
                        style = MaterialTheme.typography.displayLarge.copy(fontSize = 24.sp, color = Navy, textAlign = TextAlign.Center)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Veriliş Tarihi: 21 Haziran 2026", style = MaterialTheme.typography.bodyMedium.copy(color = TextLight))
                    
                    Divider(modifier = Modifier.padding(vertical = 16.dp))
                    
                    Text("Kazanım Özeti", style = MaterialTheme.typography.labelSmall.copy(color = Navy))
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text("3,120 kg CO₂ Kazanımı", style = MaterialTheme.typography.headlineMedium.copy(color = Green, fontWeight = FontWeight.ExtraBold))
                    Text("≈ 143 Ağaç", style = MaterialTheme.typography.headlineMedium.copy(color = Teal))
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Surface(
                        color = Green.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            "Tarım ve Orman Bakanlığı'na adınıza 10 fidan bağışlandı \uD83C\uDF31",
                            style = MaterialTheme.typography.bodyMedium.copy(color = Green, fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Mock QR
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("QR", color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedButton(
                    onClick = { },
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Navy)
                ) {
                    Icon(Icons.Default.Share, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Paylaş")
                }
                Button(
                    onClick = { },
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Navy)
                ) {
                    Icon(Icons.Default.Download, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("PDF İndir")
                }
            }
        }
    }
}
