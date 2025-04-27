package com.example.gerenciadorviagem.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gerenciadorviagem.dao.TripDao
import com.example.gerenciadorviagem.viewmodel.NewTripViewModel

class NewTripViewModelFactory(
    private val tripDao: TripDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewTripViewModel(tripDao) as T
    }
}
