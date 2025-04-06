package com.example.gerenciadorviagem.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gerenciadorviagem.dao.UserDao
import com.example.gerenciadorviagem.entity.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UsuarioCadastro(
    val user: String = "",
    val email : String = "",
    val senha : String = "",
    val confirmarSenha : String = "",
    val errorMessage : String = "",
    val isSaved : Boolean = false
) {
    fun validatePassord(): String {
        if (senha.isBlank()) {
            return "Senha é obrigatório"
        }
        return ""
    }
    fun validateConfirmPassword(): String {
        if (confirmarSenha != senha) {
            return "As senhas não conferem"
        }
        return ""
    }
    fun validateAllFields() {
        if (user.isBlank()){
            throw Exception("Nome é obrigatório")
        }
        if (email.isBlank()){
            throw Exception("E-mail é obrigatório")
        }
        if (validatePassord().isNotBlank()) {
            throw Exception(validatePassord())
        }
        if (validateConfirmPassword().isNotBlank()) {
            throw Exception(validateConfirmPassword())
        }
        }
    fun toUser(): User {
        return User (
            name = user,
            email = email,
            password = senha
        )

    }
}

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