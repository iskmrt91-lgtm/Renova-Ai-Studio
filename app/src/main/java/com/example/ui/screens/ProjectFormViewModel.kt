package com.example.ui.screens

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProjectFormViewModel : ViewModel() {
    private val _il = MutableStateFlow("")
    val il = _il.asStateFlow()

    private val _ilce = MutableStateFlow("")
    val ilce = _ilce.asStateFlow()

    private val _mahalle = MutableStateFlow("")
    val mahalle = _mahalle.asStateFlow()

    private val _adaParsel = MutableStateFlow("")
    val adaParsel = _adaParsel.asStateFlow()

    private val _insaatYili = MutableStateFlow("")
    val insaatYili = _insaatYili.asStateFlow()

    private val _wasteItems = MutableStateFlow<List<WasteItem>>(emptyList())
    val wasteItems = _wasteItems.asStateFlow()

    private val _isAnalyzing = MutableStateFlow(false)
    val isAnalyzing = _isAnalyzing.asStateFlow()

    private val geminiUseCase = GeminiVisionUseCase()

    fun updateField(field: String, value: String) {
        when (field) {
            "il" -> _il.value = value
            "ilce" -> _ilce.value = value
            "mahalle" -> _mahalle.value = value
            "adaParsel" -> _adaParsel.value = value
            "insaatYili" -> _insaatYili.value = value
        }
    }

    fun analyzeImage(bitmap: Bitmap, apiKey: String) {
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            // For prototyping, we could mock or fail gracefully
            // If they don't have an API key, we simulate an error
            return
        }
        
        _isAnalyzing.value = true
        viewModelScope.launch {
            val detected = geminiUseCase.analyzeWasteImage(bitmap, apiKey)
            val newItems = detected.mapNotNull { detectedItem ->
                val material = MaterialType.fromId(detectedItem.id) ?: return@mapNotNull null
                WasteItem(
                    material = material,
                    quantity = detectedItem.quantity,
                    isAiDetected = true,
                    lowConfidence = detectedItem.confidence < 0.7
                )
            }
            
            // Append or merge
            val current = _wasteItems.value.toMutableList()
            current.addAll(newItems)
            _wasteItems.value = current
            _isAnalyzing.value = false
        }
    }

    fun addManualItem(material: MaterialType, quantity: Double) {
        val current = _wasteItems.value.toMutableList()
        current.add(WasteItem(material = material, quantity = quantity, isAiDetected = false))
        _wasteItems.value = current
    }

    fun updateItemQuantity(index: Int, newQuantity: Double) {
        val current = _wasteItems.value.toMutableList()
        if (index in current.indices) {
            val item = current[index]
            current[index] = item.copy(quantity = newQuantity, lowConfidence = false) // user confirmed/edited
            _wasteItems.value = current
        }
    }

    fun removeItem(index: Int) {
        val current = _wasteItems.value.toMutableList()
        if (index in current.indices) {
            current.removeAt(index)
            _wasteItems.value = current
        }
    }
}
