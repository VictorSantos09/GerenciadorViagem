package com.example.gerenciadorviagem.screens

import androidx.lifecycle.ViewModel
import com.example.gerenciadorviagem.dao.UserDao
import com.example.gerenciadorviagem.data.LoginrUserViewModel

class LoginUserViewModelFactory (private val userDao: UserDao): androidx.lifecycle.ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginrUserViewModel(userDao) as T
    }
}
