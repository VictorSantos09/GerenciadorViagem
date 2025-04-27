package com.example.gerenciadorviagem.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gerenciadorviagem.dao.UserDao
import com.example.gerenciadorviagem.data.UsuarioCadastro
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterUserViewModel(
    private val userDao: UserDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(UsuarioCadastro())
    val uiState : StateFlow<UsuarioCadastro> = _uiState.asStateFlow()

    fun onUserChange(user: String) {
        _uiState.value = _uiState.value.copy(user = user)
    }
    fun onEmailChange(email:String){
        _uiState.value = _uiState.value.copy(email = email)
    }
    fun onSenhaChange(senha:String){
        _uiState.value = _uiState.value.copy(senha = senha)
    }
    fun onConfirmarSenhaChange(senha:String){
        _uiState.value = _uiState.value.copy(confirmarSenha = senha)
    }

    fun register()  {
        try {
            _uiState.value.validateAllFields()

            viewModelScope.launch {
                userDao.insert(_uiState.value.toUser())
                _uiState.value = _uiState.value.copy(isSaved = true)
            }
        }
        catch (e: Exception) {
            _uiState.value = _uiState.value.copy(errorMessage = e.message ?: "Unknow error")
        }
    }
    fun cleanDisplayValues() {
        _uiState.value = _uiState.value.copy(
            isSaved = false,
            errorMessage = "")
    }
}