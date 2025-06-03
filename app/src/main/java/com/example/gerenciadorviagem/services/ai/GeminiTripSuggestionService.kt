package com.restaurant.travel_counselor.services.ai

import android.content.Context
import com.example.gerenciadorviagem.services.ai.loadGeminiApiKey
import com.google.ai.client.generativeai.GenerativeModel
import com.restaurant.travel_counselor.services.ai.dto.TripSuggestionRequest
import com.restaurant.travel_counselor.services.ai.dto.TripSuggestionResponse

class GeminiTripSuggestionService(context: Context) : TripSuggestionService {

    private val model = GenerativeModel(
        modelName = "gemini-2.0-flash",
        apiKey = loadGeminiApiKey(context)
            ?: throw IllegalStateException("GEMINI_API_KEY not found in secrets.properties")
    )

    override suspend fun generateSuggestion(request: TripSuggestionRequest): TripSuggestionResponse {
        val prompt = buildPrompt(request)

        val response = model.generateContent(prompt)
        val text = response.text ?: "No response received."

        return TripSuggestionResponse(itinerary = text)
    }

    private fun buildPrompt(request: TripSuggestionRequest): String {
        return """
        Você é um assistente de viagens. Crie um roteiro diário e conciso para a viagem ao destino "${request.destination}".
        A viagem ocorre de ${request.startDate} a ${request.endDate}, sendo uma viagem do tipo "${request.type}".
        O orçamento total disponível é de R$${"%.2f".format(request.budget)}.

        Considere as seguintes observações e preferências do viajante: ${request.notes}.

        O roteiro deve ser prático e direto, com atividades plausíveis para os dias de viagem.

        **Importante:** A resposta deve estar completamente em **português** e **não deve conter nenhuma formatação Markdown** (ex: asteriscos, hashtags, listas com traços ou números, ou quebras desnecessárias). 
        Utilize apenas **texto simples (plain text)**. Evite símbolos de marcação ou qualquer estilização. Responda apenas com o roteiro, sem introdução ou encerramento.
    """.trimIndent()
    }
}
