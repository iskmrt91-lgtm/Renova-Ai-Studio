package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.*

data class Coordinate(val lat: Double, val lng: Double)

data class PickupPoint(
    val id: String,
    val location: Coordinate,
    val title: String,
    val address: String,
    val materialSummary: String,
    val co2e: Double,
    val distanceKm: Double,
    var isReserved: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(onNavigateToPassport: () -> Unit) {
    var selectedDistance by remember { mutableStateOf(5) }
    
    // Mock Data
    var points by remember { mutableStateOf(listOf(
        PickupPoint("1", Coordinate(41.05, 28.98), "Renova Şişli Projesi", "Şişli, Merkez Mah.", "1200kg Ahşap, 400kg PVC", 890.0, 3.2),
        PickupPoint("2", Coordinate(40.98, 29.02), "Kadıköy Kentsel Dönüşüm", "Kadıköy, Moda", "3000kg Beton, 800kg Demiri", 2100.0, 8.5),
        PickupPoint("3", Coordinate(41.02, 28.93), "Fatih Yıkım Alanı", "Fatih, Balat", "450kg Cam, 120kg Alüminyum", 1120.0, 1.8)
    )) }

    val filteredPoints = points.filter { it.distanceKm <= selectedDistance }
    var selectedPoint by remember { mutableStateOf<PickupPoint?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Atık Noktaları", color = Navy, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = OffWhite)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            // Map Placeholder
            Box(modifier = Modifier.fillMaxWidth().height(300.dp).background(Color(0xFFE8EAF6))) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawLine(Color.White, start = Offset(0f, size.height * 0.3f), end = Offset(size.width, size.height * 0.4f), strokeWidth = 12f)
                    drawLine(Color.White, start = Offset(size.width * 0.4f, 0f), end = Offset(size.width * 0.6f, size.height), strokeWidth = 16f)
                    drawLine(Color.White, start = Offset(0f, size.height * 0.7f), end = Offset(size.width, size.height * 0.8f), strokeWidth = 8f)
                    drawLine(Color.White, start = Offset(size.width * 0.2f, 0f), end = Offset(size.width * 0.3f, size.height), strokeWidth = 8f)
                }
                
                filteredPoints.forEachIndexed { index, point ->
                    val align = when(index % 3) {
                        0 -> Alignment.TopCenter
                        1 -> Alignment.BottomEnd
                        else -> Alignment.CenterStart
                    }
                    val pad = when(index % 3) {
                        0 -> PaddingValues(top = 60.dp, start = 40.dp)
                        1 -> PaddingValues(bottom = 80.dp, end = 60.dp)
                        else -> PaddingValues(start = 50.dp, bottom = 20.dp)
                    }
                    
                    Box(modifier = Modifier.align(align).padding(pad)) {
                        IconButton(onClick = { selectedPoint = point }) {
                            Icon(Icons.Default.LocationOn, contentDescription = "Pin", tint = Teal, modifier = Modifier.size(48.dp))
                        }
                    }
                }
                
                // Filters overlays
                LazyRow(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(2, 5, 10).forEach { dist ->
                        item {
                            FilterChip(
                                selected = selectedDistance == dist,
                                onClick = { selectedDistance = dist },
                                label = { Text("$dist km") },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Navy,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                }
            }

            // Offers List
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredPoints) { p ->
                    OfferCard(
                        point = p,
                        onAccept = {
                            points = points.map { if (it.id == p.id) it.copy(isReserved = true) else it }
                        },
                        onNavigate = { },
                        onPhotoUpload = onNavigateToPassport
                    )
                }
            }
        }
        
        if (selectedPoint != null) {
            Dialog(onDismissRequest = { selectedPoint = null }) {
                OfferCard(
                    point = selectedPoint!!,
                    onAccept = {
                        points = points.map { if (it.id == selectedPoint!!.id) it.copy(isReserved = true) else it }
                        selectedPoint = null
                    },
                    onNavigate = { selectedPoint = null },
                    onPhotoUpload = {
                        selectedPoint = null
                        onNavigateToPassport()
                    }
                )
            }
        }
    }
}

@Composable
fun OfferCard(
    point: PickupPoint,
    onAccept: () -> Unit,
    onNavigate: () -> Unit,
    onPhotoUpload: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, Slate200)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(point.title, style = MaterialTheme.typography.headlineMedium.copy(color = Navy))
                if (point.isReserved) {
                    Surface(color = AmberBg, border = BorderStroke(1.dp, AmberBorder), shape = RoundedCornerShape(8.dp)) {
                        Text("Rezerve · 24 saat", color = AmberTextDark, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(6.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn, contentDescription = null, tint = Teal, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("${point.address} (${point.distanceKm} km)", style = MaterialTheme.typography.bodyMedium.copy(color = TextLight))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(point.materialSummary, style = MaterialTheme.typography.bodyLarge.copy(color = Navy))
            Spacer(modifier = Modifier.height(4.dp))
            Text("Potansiyel: ${point.co2e} kg CO₂e", style = MaterialTheme.typography.labelSmall.copy(color = Green))
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (!point.isReserved) {
                Button(
                    onClick = onAccept,
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Green)
                ) {
                    Icon(Icons.Default.Check, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Atığı Kabul Et")
                }
            } else {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = onNavigate,
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Teal)
                    ) {
                        Icon(Icons.Default.Navigation, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Git", maxLines = 1)
                    }
                    Button(
                        onClick = onPhotoUpload,
                        modifier = Modifier.weight(1.5f).height(48.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Navy)
                    ) {
                        Icon(Icons.Default.CameraAlt, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Teslim Fotoğrafı", maxLines = 1)
                    }
                }
            }
        }
    }
}
