package com.example.gerenciadorviagem.shared.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
object DateUtils {
    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    fun formatDate(date: LocalDate): String {
        return date.format(formatter)
    }
}
