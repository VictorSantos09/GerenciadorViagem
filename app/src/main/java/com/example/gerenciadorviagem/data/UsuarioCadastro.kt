package com.example.gerenciadorviagem.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class UsuarioCadastro(
    val email : String = "",
    val senha : String = "",
    val confirmarsenha : String = "",
    val errorMessage : String = ""
) {
    fun validateAllFields() {
        if (email.isBlank()){
            throw Exception("E-mail é obrigatório")
        }
        if (senha.isBlank()){
            throw Exception("Senha é obrigatório")
        }
        if (confirmarsenha.isBlank()) {
            throw Exception("Confirme sua senha")
        }
        else if (confirmarsenha != senha){
            throw Exception("As senhas não conferem")
        }
    }
}

class RegisterUserViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UsuarioCadastro())
    val uiState : StateFlow<UsuarioCadastro> = _uiState.asStateFlow()

    fun onEmailChange(email:String){
        _uiState.value = _uiState.value.copy(email = email)
    }
    fun onSenhaChange(senha:String){
        _uiState.value = _uiState.value.copy(senha = senha)
    }
    fun onConfirmarSenhaChange(senha:String){
        _uiState.value = _uiState.value.copy(confirmarsenha = senha)
    }
    fun register():Boolean {
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