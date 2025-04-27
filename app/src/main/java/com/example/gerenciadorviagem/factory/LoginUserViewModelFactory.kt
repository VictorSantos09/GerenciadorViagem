package com.example.gerenciadorviagem.factory
import androidx.lifecycle.ViewModel
import com.example.gerenciadorviagem.dao.UserDao
import com.example.gerenciadorviagem.viewmodel.LoginUserViewModel

class LoginUserViewModelFactory(
    private val userDao: UserDao
):androidx.lifecycle.ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginUserViewModel(userDao) as T
    }
}