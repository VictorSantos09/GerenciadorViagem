package com.example.gerenciadorviagem.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gerenciadorviagem.dao.TripDao
import com.example.gerenciadorviagem.entity.Trip
import com.example.gerenciadorviagem.data.NewTripData
import com.example.gerenciadorviagem.entity.TripType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NewTripViewModel(
    private val tripDao: TripDao
) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    private val uiFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    private val _uiState = MutableStateFlow(NewTripData())
    val uiState: StateFlow<NewTripData> = _uiState

    val tripList: StateFlow<List<Trip>> = tripDao.findAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onDestinationChange(newDestination: String) {
        _uiState.value = _uiState.value.copy(destination = newDestination)
    }

    fun onTripTypeChange(newTripType: TripType) {
        _uiState.value = _uiState.value.copy(tripType = newTripType)
    }

    fun onStartDateChange(newStartDate: String) {
        _uiState.value = _uiState.value.copy(startDate = newStartDate)
    }

    fun onEndDateChange(newEndDate: String) {
        _uiState.value = _uiState.value.copy(endDate = newEndDate)
    }

    fun onBudgetChange(newBudget: Double) {
        _uiState.value = _uiState.value.copy(budget = newBudget)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveTrip(onSuccess: () -> Unit, onError: () -> Unit, id: Int) {
        val tripData = _uiState.value
        if (tripData.validateFields().isNotBlank()) {
            onError()
            return
        }

        val trip = Trip(
            id = id,
            destination = tripData.destination,
            tripType = tripData.tripType,
            startDate = LocalDate.parse(tripData.startDate, uiFormatter),
            endDate = LocalDate.parse(tripData.endDate, uiFormatter),
            budget = tripData.budget
        )

        viewModelScope.launch {
            if (id != 0) {
                tripDao.updateTrip(trip)
            } else {
                tripDao.insertTrip(trip)
            }
            onSuccess()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadTripById(id: Int) {
        viewModelScope.launch {
            val trip = tripDao.findById(id)
            trip?.let {
                _uiState.value = NewTripData(
                    destination = it.destination,
                    tripType = it.tripType,
                    startDate = it.startDate.format(uiFormatter),
                    endDate = it.endDate.format(uiFormatter),
                    budget = it.budget
                )
            }
        }
    }
}
