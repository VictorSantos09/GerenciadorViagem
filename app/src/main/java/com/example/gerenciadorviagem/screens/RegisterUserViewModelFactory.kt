package com.example.gerenciadorviagem.screens

import androidx.lifecycle.ViewModel
import com.example.gerenciadorviagem.dao.UserDao
import com.example.gerenciadorviagem.data.RegisterUserViewModel

class RegisterUserViewModelFactory (
    private val userDao : UserDao
) : androidx.lifecycle.ViewModelProvider.Factory
    {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RegisterUserViewModel(userDao) as T
        }
    }
