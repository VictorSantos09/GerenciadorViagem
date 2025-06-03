package com.restaurant.travel_counselor.services.ai

import com.restaurant.travel_counselor.services.ai.dto.TripSuggestionRequest
import com.restaurant.travel_counselor.services.ai.dto.TripSuggestionResponse

interface TripSuggestionService {
    suspend fun generateSuggestion(request: TripSuggestionRequest): TripSuggestionResponse
}
