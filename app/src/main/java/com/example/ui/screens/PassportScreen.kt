package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassportScreen(onNavigateToCertificate: () -> Unit) {
    Scaffold(
        topBar = {
            Surface(
                color = Color.White,
                shadowElevation = 1.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(Navy, RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .border(2.dp, Green, CircleShape)
                            )
                        }
                        Column {
                            Text(
                                text = "RENOVA",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    lineHeight = 18.sp,
                                    letterSpacing = (-0.5).sp,
                                    color = Navy
                                )
                            )
                            Text(
                                text = "ATIK YÖNETİMİ",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Medium,
                                    letterSpacing = 1.sp,
                                    color = Navy.copy(alpha = 0.6f)
                                )
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(Slate100, CircleShape)
                            .border(1.dp, Slate200, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("MT", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Navy))
                    }
                }
            }
        },
        containerColor = OffWhite,
        bottomBar = {
            Surface(
                color = Color.White,
                shadowElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Just simple mock buttons for now as bottom nav is not fully functional
                    Text("Ana Sayfa", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Slate400)
                    Text("Projeler", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Slate400)
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .offset(y = (-16).dp)
                            .background(Green, CircleShape)
                            .border(4.dp, OffWhite, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("+", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    }
                    Text("Pasaport", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Navy)
                    Text("Sertifika", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Slate400)
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Envanter Raporu",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-0.5).sp,
                        color = Navy
                    )
                )
                Text(
                    text = "Proje ID: #K-340291 • Kadıköy",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp,
                        color = Slate500
                    )
                )
            }

            // Toplam CO2e Kazanimi Card
            Card(
                modifier = Modifier.fillMaxWidth().height(128.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Navy)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(20.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = "TOPLAM CO₂E KAZANIMI",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.8f),
                                letterSpacing = 1.sp
                            )
                        )
                        Surface(
                            color = Green,
                            shape = CircleShape
                        ) {
                            Text(
                                "SERTİFİKALI",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 10.sp,
                                    color = Navy,
                                    fontWeight = FontWeight.ExtraBold
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                    Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            "1.482,5",
                            style = MaterialTheme.typography.displayLarge.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            "kg",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 18.sp
                            ),
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }
                }
            }

            // Mini Cards Row
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                // Geri Donusum Card
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Slate200)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            "GERİ DÖNÜŞÜM",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, color = Slate400, letterSpacing = 1.sp)
                        )
                        Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("3.840", style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp, color = Navy))
                            Text("kg", style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp, color = Slate400), modifier = Modifier.padding(bottom = 2.dp))
                        }
                        Box(modifier = Modifier.fillMaxWidth().height(4.dp).clip(CircleShape).background(Slate100)) {
                            Box(modifier = Modifier.fillMaxWidth(0.75f).fillMaxHeight().background(Teal))
                        }
                    }
                }

                // Yakit Tasarrufu Card
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Slate200)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            "YAKIT TASARRUFU",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, color = Slate400, letterSpacing = 1.sp)
                        )
                        Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("412", style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp, color = Navy))
                            Text("lt", style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp, color = Slate400), modifier = Modifier.padding(bottom = 2.dp))
                        }
                        Box(modifier = Modifier.fillMaxWidth().height(4.dp).clip(CircleShape).background(Slate100)) {
                            Box(modifier = Modifier.fillMaxWidth(0.5f).fillMaxHeight().background(Amber))
                        }
                    }
                }
            }

            // Malzeme Dagilimi Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Slate200)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("Malzeme Dağılımı", style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Navy))
                        Text("+12% Artış", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, color = Green, fontWeight = FontWeight.Bold))
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Bar Chart Approximation
                    Row(
                        modifier = Modifier.fillMaxWidth().height(80.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Box(modifier = Modifier.weight(1f).fillMaxHeight(0.8f).clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)).background(Teal))
                        Box(modifier = Modifier.weight(1f).fillMaxHeight(0.6f).clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)).background(Green))
                        Box(modifier = Modifier.weight(1f).fillMaxHeight(0.45f).clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)).background(Navy))
                        Box(modifier = Modifier.weight(1f).fillMaxHeight(0.3f).clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)).background(Amber))
                        Box(modifier = Modifier.weight(1f).fillMaxHeight(0.15f).clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)).background(Slate200))
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Divider(color = Slate100)
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Teal))
                            Text("İnşaat Demiri", style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp, color = Navy))
                        }
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Green))
                            Text("Ahşap Kapı", style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp, color = Navy))
                        }
                    }
                }
            }

            // Warning Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = AmberBg),
                border = BorderStroke(1.dp, AmberBorder)
            ) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("!", style = MaterialTheme.typography.headlineMedium.copy(color = Amber, fontWeight = FontWeight.Bold))
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("ASBEST UYARISI", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, color = AmberTextDark, letterSpacing = 1.sp))
                        Text("1999 öncesi yapı: Asbest içerikli malzeme riski. Uzman tespiti önerilir.", style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp, color = AmberTextDark))
                    }
                }
            }

            Button(
                onClick = onNavigateToCertificate,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Navy),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Sertifika Görüntüle", color = Color.White, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

