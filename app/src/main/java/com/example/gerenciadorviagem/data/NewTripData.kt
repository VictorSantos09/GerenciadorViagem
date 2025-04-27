package com.example.gerenciadorviagem.data

data class NewTripData(
    val destination: String = "",
    val tripType: String = "Lazer",
    val startDate: String = "",
    val endDate: String = "",
    val budget: String = ""
) {
    fun validateFields(): String {
        return when {
            destination.isBlank() -> "Destino é obrigatório"
            startDate.isBlank() -> "Data de início é obrigatório"
            endDate.isBlank() -> "Data final é obrigatório"
            budget.isBlank() -> "Orçamento é obrigatório"
            else -> ""
        }
    }
}
