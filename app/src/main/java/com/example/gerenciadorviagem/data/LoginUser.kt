package com.example.gerenciadorviagem.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class LoginUser(
    val email : String = "",
    val senha : String = "",
    val errorMessage : String = ""
) {
    fun validateAllFields() {
        if (email.isBlank()){
            throw Exception("Informe o email.")
        }
        if (senha.isBlank()){
            throw Exception("Informe a senha")
        }
    }
}

class LoginrUserViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUser())
    val uiState : StateFlow<LoginUser> = _uiState.asStateFlow()

    fun onEmailChange(email:String){
        _uiState.value = _uiState.value.copy(email = email)
    }
    fun onSenhaChange(senha:String){
        _uiState.value = _uiState.value.copy(senha = senha)

    }
    fun login():Boolean {
        return try {
            _uiState.value.validateAllFields()
            true
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(errorMessage = e.message ?: "Unknown error")
            false
        }
    }
    fun cleanErrorMessage() {
        _uiState.value = _uiState.value.copy(errorMessage = "")
    }
}