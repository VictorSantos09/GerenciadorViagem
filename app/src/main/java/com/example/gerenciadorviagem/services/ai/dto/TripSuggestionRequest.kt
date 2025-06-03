package com.restaurant.travel_counselor.services.ai.dto

data class TripSuggestionRequest(
    val destination: String,
    val startDate: String,
    val endDate: String,
    val type: String,
    val budget: Double,
    val notes: String
)
