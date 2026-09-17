package com.example.model

import android.graphics.Bitmap
import android.util.Base64
import com.example.api.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*
import java.io.ByteArrayOutputStream

@Serializable
data class DetectedMaterial(
    val id: String,
    val unit: String,
    val quantity: Double,
    val confidence: Double
)

class GeminiVisionUseCase {
    private fun Bitmap.toBase64(): String {
        val outputStream = ByteArrayOutputStream()
        // Resize bitmap to avoid sending too large images, keeping aspect ratio
        val maxDim = 1024
        val scale = Math.min(maxDim.toFloat() / width, maxDim.toFloat() / height)
        val scaledBitmap = if (scale < 1) Bitmap.createScaledBitmap(this, (width * scale).toInt(), (height * scale).toInt(), true) else this

        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        return Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP)
    }

    suspend fun analyzeWasteImage(bitmap: Bitmap, apiKey: String): List<DetectedMaterial> {
        val prompt = """
            Analyze the provided image of construction or demolition waste.
            Identify any of the following materials if present:
            - ahsap_kapi (unit: adet)
            - ahsap_mobilya (unit: adet)
            - parke (unit: m2)
            - pvc (unit: adet)
            - cam (unit: m2)
            - insaat_demiri (unit: kg)
            - demir_korkuluk (unit: m)
            - aluminyum (unit: m2)
            - bataryalar (unit: adet)
            
            Return ONLY a valid JSON array of objects with the structure:
            { "id": "material_id", "unit": "unit_string", "quantity": number, "confidence": number (0.0 to 1.0) }
            Estimate the quantity as best as possible. Do not include markdown formatting or json backticks.
        """.trimIndent()

        val requestBody = GenerateContentRequest(
            contents = listOf(
                Content(
                    parts = listOf(
                        Part(text = prompt),
                        Part(inlineData = InlineData(mimeType = "image/jpeg", data = bitmap.toBase64()))
                    )
                )
            ),
            generationConfig = GenerationConfig(
                responseFormat = ResponseFormat(
                    text = ResponseFormatText(
                        mimeType = "application/json",
                        schema = buildJsonObject {
                            put("type", "ARRAY")
                            putJsonObject("items") {
                                put("type", "OBJECT")
                                putJsonObject("properties") {
                                    putJsonObject("id") { put("type", "STRING") }
                                    putJsonObject("unit") { put("type", "STRING") }
                                    putJsonObject("quantity") { put("type", "NUMBER") }
                                    putJsonObject("confidence") { put("type", "NUMBER") }
                                }
                                putJsonArray("required") {
                                    add("id")
                                    add("unit")
                                    add("quantity")
                                    add("confidence")
                                }
                            }
                        }
                    )
                )
            )
        )

        try {
            val response = RetrofitClient.service.generateContent(apiKey, requestBody)
            val jsonText = response.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: "[]"
            
            return Json { ignoreUnknownKeys = true }.decodeFromString<List<DetectedMaterial>>(jsonText)
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }
}
