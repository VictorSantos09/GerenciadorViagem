package com.example.gerenciadorviagem.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gerenciadorviagem.dao.TripDao
import com.example.gerenciadorviagem.entity.Trip
import com.example.gerenciadorviagem.data.NewTripData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NewTripViewModel(
    private val tripDao: TripDao
) : ViewModel() {

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

    fun onTripTypeChange(newTripType: String) {
        _uiState.value = _uiState.value.copy(tripType = newTripType)
    }

    fun onStartDateChange(newStartDate: String) {
        _uiState.value = _uiState.value.copy(startDate = newStartDate)
    }

    fun onEndDateChange(newEndDate: String) {
        _uiState.value = _uiState.value.copy(endDate = newEndDate)
    }

    fun onBudgetChange(newBudget: String) {
        _uiState.value = _uiState.value.copy(budget = newBudget)
    }

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
            startDate = tripData.startDate,
            endDate = tripData.endDate,
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

    fun loadTripById(id: Int) {
        viewModelScope.launch {
            val trip = tripDao.findById(id)
            trip?.let {
                _uiState.value = NewTripData(
                    destination = it.destination,
                    tripType = it.tripType,
                    startDate = it.startDate,
                    endDate = it.endDate,
                    budget = it.budget
                )
            }
        }
    }
}
