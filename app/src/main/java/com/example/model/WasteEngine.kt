package com.example.model

enum class MaterialType(val id: String, val displayName: String, val kgPerUnit: Double, val co2eFactor: Double) {
    AHSAP_KAPI("ahsap_kapi", "Ahşap Kapı", 30.0, 0.28),
    AHSAP_MOBILYA("ahsap_mobilya", "Ahşap Mobilya", 70.0, 0.28),
    PARKE("parke", "Parke (m²)", 9.0, 0.28),
    PVC("pvc", "PVC Pencere/Kapı", 22.0, 2.0),
    CAM("cam", "Cam (m²)", 12.0, 0.67),
    INSAAT_DEMIRI("insaat_demiri", "İnşaat Demiri (kg)", 1.0, 0.95), // Entered in kg directly
    DEMIR_KORKULUK("demir_korkuluk", "Demir Korkuluk (m)", 12.0, 0.95),
    ALUMINYUM("aluminyum", "Alüminyum (m²)", 7.0, 6.86),
    BATARYALAR("bataryalar", "Bataryalar", 1.8, 2.0);

    companion object {
        fun fromId(id: String): MaterialType? = values().find { it.id == id }
    }
}

data class WasteItem(
    val material: MaterialType,
    val quantity: Double,
    val isAiDetected: Boolean = false,
    val lowConfidence: Boolean = false
) {
    val totalKg: Double get() = quantity * material.kgPerUnit
    val co2eSaved: Double get() = totalKg * material.co2eFactor
}

object Co2Engine {
    fun calculateTrees(totalCo2e: Double): Int {
        return (totalCo2e / 21.77).toInt()
    }

    fun isAsbestosRisk(buildYear: Int): Boolean {
        return buildYear < 1999
    }
}
