package com.example.gerenciadorviagem.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class LoginUser(
    val usuario : String = "",
    val senha : String = "",
    val errorMessage : String = ""
) {
    fun validateAllFields() {
        if (usuario.isBlank()){
            throw Exception("Informe o nome de usuário.")
        }
        if (senha.isBlank()){
            throw Exception("Informe a senha")
        }
    }
}

class LoginrUserViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UsuarioCadastro())
    val uiState : StateFlow<UsuarioCadastro> = _uiState.asStateFlow()

    fun onEmailChange(email:String){
        _uiState.value = _uiState.value.copy(email = email)
    }
    fun onLoginChange(login: String){
        _uiState.value = _uiState.value.copy(login = login)

    }
    fun onSenhaChange(senha:String){
        _uiState.value = _uiState.value.copy(senha = senha)

    }
    fun onConfirmarSenhaChange(senha:String){
        _uiState.value = _uiState.value.copy(confirmarsenha = senha)

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