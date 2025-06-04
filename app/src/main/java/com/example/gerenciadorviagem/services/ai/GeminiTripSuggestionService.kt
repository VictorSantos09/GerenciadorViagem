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
        Você é um especialista em planejamento de viagens. Crie um roteiro diário, objetivo e realista para uma viagem ao destino "${request.destination}", que ocorrerá entre os dias ${request.startDate} e ${request.endDate}. O tipo da viagem é: "${request.type}".

        O orçamento total disponível é de R$${"%.2f".format(request.budget)}. 

        Leve em conta as seguintes observações e preferências do viajante: ${request.notes}.

        Para cada dia da viagem, descreva de forma clara as atividades recomendadas, respeitando o orçamento, o tipo de viagem e as preferências indicadas.

        **Instruções importantes**:
        - A resposta deve estar inteiramente em **português**.
        - Responda apenas com o roteiro. **Não inclua introdução, explicações ou encerramento**.
        - Use **texto simples (plain text)**. **Não utilize formatação Markdown** como listas numeradas, asteriscos, traços, emojis ou qualquer simbolismo visual.
        - Organize o roteiro com separação por dia de forma clara, utilizando apenas texto.

        Exemplo de formatação esperada:
        Dia 1: Visita ao centro histórico, almoço em restaurante local, passeio no parque à tarde.

        Dia 2: Café da manhã em padaria típica, trilha leve na região, descanso à noite.
    """.trimIndent()
    }

}
