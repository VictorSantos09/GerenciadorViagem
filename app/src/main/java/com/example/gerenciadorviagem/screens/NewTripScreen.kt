package com.example.gerenciadorviagem.screens

import RequiredTextField
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gerenciadorviagem.database.AppDatabase
import com.example.gerenciadorviagem.viewmodel.NewTripViewModel
import com.example.gerenciadorviagem.factory.NewTripViewModelFactory
import com.example.gerenciadorviagem.components.MyDatePickerField
import com.example.gerenciadorviagem.components.RequiredNumberField
import com.example.gerenciadorviagem.entity.TripType
import com.example.gerenciadorviagem.shared.Routes
import java.text.SimpleDateFormat
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTripScreen(onNavigateTo: (String) -> Unit, id: Int?) {
    val context = LocalContext.current
    val tripDao = AppDatabase.getDatabase(context).tripDao()
    val factory = NewTripViewModelFactory(tripDao)
    val newTripViewModel: NewTripViewModel = viewModel(factory = factory)
    val uiState = newTripViewModel.uiState.collectAsState()

    LaunchedEffect(id) {
        if (id != null) {
            newTripViewModel.loadTripById(id)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Nova Viagem") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Preencha os dados da viagem",
                modifier = Modifier.padding(bottom = 24.dp),
            )

            RequiredTextField(
                label = "Destino",
                value = uiState.value.destination,
                onValueChange = newTripViewModel::onDestinationChange
            )

            TripTypeSelector(
                selectedType = uiState.value.tripType,
                onTypeChange = newTripViewModel::onTripTypeChange
            )

            val sdf = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
            val startDateMillis = remember(uiState.value.startDate) {
                try {
                    sdf.parse(uiState.value.startDate)?.time
                } catch (e: Exception) {
                    null
                }
            }

            MyDatePickerField(
                label = "Data de Início",
                date = uiState.value.startDate,
                onDateChange = newTripViewModel::onStartDateChange,
                minDate = System.currentTimeMillis()
            )

            MyDatePickerField(
                label = "Data de Fim",
                date = uiState.value.endDate,
                onDateChange = newTripViewModel::onEndDateChange,
                minDate = startDateMillis
            )

            RequiredNumberField(
                label = "Orçamento (R$)",
                value = uiState.value.budget.toString(),
                onValueChange = { input ->
                    val budget = input.toDoubleOrNull() ?: 0.0
                    newTripViewModel.onBudgetChange(budget)
                }
            )

            Button(
                onClick = {
                    newTripViewModel.saveTrip(
                        id = id ?: 0,
                        onSuccess = {
                            Toast.makeText(context, "Viagem salva com sucesso!", Toast.LENGTH_SHORT).show()
                            onNavigateTo(Routes.TELA_PRINCIPAL)
                        },
                        onError = {
                            Toast.makeText(context, "Preencha todos os campos corretamente!", Toast.LENGTH_SHORT).show()
                        }
                    )
                },
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth(0.7f)
            ) {
                Text(text = "Salvar")
            }
        }
    }
}

@Composable
fun TripTypeSelector(selectedType: TripType, onTypeChange: (TripType) -> Unit) {
    Row {
        RadioButton(
            selected = selectedType == TripType.LAZER,
            onClick = { onTypeChange(TripType.LAZER) }
        )
        Text("Lazer")

        RadioButton(
            selected = selectedType == TripType.TRABALHO,
            onClick = { onTypeChange(TripType.TRABALHO) }
        )
        Text("Trabalho")
    }
}

@Composable
fun RadioButtonWithLabel(label: String, selected: String, onSelect: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected == label,
            onClick = { onSelect(label) }
        )
        Text(text = label)
    }
}