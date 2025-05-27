package com.example.gerenciadorviagem.data

import com.example.gerenciadorviagem.entity.TripType

data class NewTripData(
    val destination: String = "",
    val tripType: TripType = TripType.LAZER,
    val startDate: String = "",
    val endDate: String = "",
    val budget: Double = 0.0
) {
    fun validateFields(): String {
        return when {
            destination.isBlank() -> "Destino é obrigatório"
            startDate.isBlank() -> "Data de início é obrigatório"
            endDate.isBlank() -> "Data final é obrigatório"
            budget.isNaN() -> "Orçamento é obrigatório"
            else -> ""
        }
    }
}
