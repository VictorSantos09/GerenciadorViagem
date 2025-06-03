package com.example.gerenciadorviagem.services.ai

import android.content.Context
import java.util.*

fun loadGeminiApiKey(context: Context): String? {
    return try {
        val props = Properties()
        val inputStream = context.assets.open("secrets.properties")
        props.load(inputStream)
        props.getProperty("GEMINI_API_KEY")
    } catch (e: Exception) {
        null
    }
}
