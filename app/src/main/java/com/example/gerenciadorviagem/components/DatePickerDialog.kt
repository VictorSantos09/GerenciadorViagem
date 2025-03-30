package com.example.gerenciadorviagem.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateDialog(
    dismissRequest: () -> Unit,
    onDateChange: (LocalDate) -> Unit, // Callback para atualizar a data
    initialDate: LocalDate // A data inicial selecionada
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = dismissRequest,
        confirmButton = {
            TextButton(onClick = {
                datePickerState.selectedDateMillis?.let {
                    val newDate = Instant.ofEpochMilli(it)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                    onDateChange(newDate) // Atualiza a data corretamente
                }
                dismissRequest() // Fecha o diálogo após confirmar
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = dismissRequest) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
