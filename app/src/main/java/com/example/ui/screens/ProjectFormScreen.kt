package com.example.ui.screens

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.BuildConfig
import com.example.model.Co2Engine
import com.example.model.MaterialType
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectFormScreen(
    viewModel: ProjectFormViewModel = viewModel(),
    onPublish: () -> Unit
) {
    val il by viewModel.il.collectAsState()
    val ilce by viewModel.ilce.collectAsState()
    val mahalle by viewModel.mahalle.collectAsState()
    val adaParsel by viewModel.adaParsel.collectAsState()
    val insaatYili by viewModel.insaatYili.collectAsState()
    val wasteItems by viewModel.wasteItems.collectAsState()
    val isAnalyzing by viewModel.isAnalyzing.collectAsState()

    val context = LocalContext.current
    var showManualAddDialog by remember { mutableStateOf(false) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                val source = ImageDecoder.createSource(context.contentResolver, uri)
                val bitmap = ImageDecoder.decodeBitmap(source)
                viewModel.analyzeImage(bitmap.copy(Bitmap.Config.ARGB_8888, true), BuildConfig.GEMINI_API_KEY)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    val yearInt = insaatYili.toIntOrNull() ?: 0
    val showAsbestosWarning = yearInt in 1..1998

    val totalKg = wasteItems.sumOf { it.totalKg }
    val totalCo2e = wasteItems.sumOf { it.co2eSaved }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Yeni Proje", color = Navy, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = OffWhite)
            )
        },
        containerColor = OffWhite,
        bottomBar = {
            Surface(
                color = Color.White,
                shadowElevation = 8.dp,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Toplam", style = MaterialTheme.typography.bodyMedium.copy(color = TextDark))
                        Text(
                            text = "${"%.1f".format(totalKg)} kg • ${"%.1f".format(totalCo2e)} kg CO₂e",
                            style = MaterialTheme.typography.labelSmall.copy(color = Green, fontWeight = FontWeight.Bold)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onPublish,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Green)
                    ) {
                        Text("Geri Dönüşüm Atığı — Yayına Hazır", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .imePadding()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }

            item {
                if (showAsbestosWarning) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = AmberBg),
                        border = BorderStroke(1.dp, AmberBorder)
                    ) {
                        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text("!", style = MaterialTheme.typography.headlineMedium.copy(color = Amber, fontWeight = FontWeight.Bold))
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text("ASBEST UYARISI", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, color = AmberTextDark, letterSpacing = 1.sp))
                                Text("1999 öncesi yapı: Asbest içerikli malzeme riski. Uzman tespiti önerilir.", style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp, color = AmberTextDark))
                            }
                        }
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Slate200)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Proje Yeri", style = MaterialTheme.typography.labelSmall.copy(color = Navy))
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(value = il, onValueChange = { viewModel.updateField("il", it) }, label = { Text("İl") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Navy, unfocusedTextColor = Navy))
                            OutlinedTextField(value = ilce, onValueChange = { viewModel.updateField("ilce", it) }, label = { Text("İlçe") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Navy, unfocusedTextColor = Navy))
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(value = mahalle, onValueChange = { viewModel.updateField("mahalle", it) }, label = { Text("Mahalle") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Navy, unfocusedTextColor = Navy))
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(value = adaParsel, onValueChange = { viewModel.updateField("adaParsel", it) }, label = { Text("Tapu/Ada-Parsel") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Navy, unfocusedTextColor = Navy))
                            OutlinedTextField(value = insaatYili, onValueChange = { viewModel.updateField("insaatYili", it) }, label = { Text("İnşaat Yılı") }, modifier = Modifier.weight(1f), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Navy, unfocusedTextColor = Navy))
                        }
                    }
                }
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(
                        onClick = { cameraLauncher.launch("image/*") },
                        colors = ButtonDefaults.buttonColors(containerColor = Navy),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.weight(1f).height(48.dp)
                    ) {
                        Icon(Icons.Default.CameraAlt, contentDescription = "Kamerayla Tara")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Kamerayla Tara")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedButton(
                        onClick = { showManualAddDialog = true },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.weight(1f).height(48.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Manuel Ekle")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Manuel Ekle")
                    }
                }
                if (isAnalyzing) {
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = Green)
                    Text("Yapay Zeka ile Analiz Ediliyor...", style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(top = 4.dp))
                }
            }

            itemsIndexed(wasteItems) { index, item ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(1.dp, Slate200),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(item.material.displayName, style = MaterialTheme.typography.bodyLarge.copy(color = Navy, fontWeight = FontWeight.Bold))
                                Spacer(modifier = Modifier.width(8.dp))
                                Surface(
                                    color = if (item.isAiDetected) Teal else Color.LightGray,
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        if (item.isAiDetected) "AI" else "Manuel",
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, color = Color.White),
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                    )
                                }
                                if (item.lowConfidence) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Surface(color = Amber, shape = RoundedCornerShape(4.dp)) {
                                        Text("Kontrol Et", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, color = Color.White), modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("≈ ${"%.1f".format(item.totalKg)} kg", style = MaterialTheme.typography.bodyMedium.copy(color = TextLight))
                        }

                        // Quantity EditText
                        OutlinedTextField(
                            value = item.quantity.toString(),
                            onValueChange = { newVal ->
                                newVal.replace(",", ".").toDoubleOrNull()?.let {
                                    viewModel.updateItemQuantity(index, it)
                                }
                            },
                            modifier = Modifier.width(80.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Navy, unfocusedTextColor = Navy)
                        )

                        IconButton(onClick = { viewModel.removeItem(index) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Sil", tint = Color.Red)
                        }
                    }
                }
            }
            
            item { Spacer(modifier = Modifier.height(80.dp)) } // Bottom padding for FAB/BottomBar
        }
    }

    if (showManualAddDialog) {
        var selectedMaterial by remember { mutableStateOf(MaterialType.AHSAP_KAPI) }
        var quantity by remember { mutableStateOf("1") }
        
        AlertDialog(
            onDismissRequest = { showManualAddDialog = false },
            title = { Text("Manuel Malzeme Ekle") },
            text = {
                Column {
                    // Quick dropdown substitute since DropdownMenu can be complex inline
                    // Just listing buttons for simplicity or use a simple Column
                    LazyColumn(modifier = Modifier.heightIn(max = 250.dp)) {
                        items(MaterialType.values().size) { index ->
                            val mat = MaterialType.values()[index]
                            val isSelected = mat == selectedMaterial
                            Row(
                                verticalAlignment = Alignment.CenterVertically, 
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedMaterial = mat }
                                    .padding(4.dp)
                            ) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { selectedMaterial = mat },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = Color(0xFF34C759),
                                        unselectedColor = Color.LightGray
                                    )
                                )
                                Text(mat.displayName)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = quantity,
                        onValueChange = { quantity = it },
                        label = { Text("Miktar") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color(0xFF0E2A47),
                            unfocusedTextColor = Color(0xFF0E2A47),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val parsed = quantity.replace(",", ".").toDoubleOrNull()
                        if (parsed != null) {
                            viewModel.addManualItem(selectedMaterial, parsed)
                        }
                        showManualAddDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Green)
                ) { Text("Ekle", color = Color.White) }
            },
            dismissButton = {
                TextButton(onClick = { showManualAddDialog = false }) { Text("İptal") }
            }
        )
    }
}
