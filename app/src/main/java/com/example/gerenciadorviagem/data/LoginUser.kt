package com.example.gerenciadorviagem.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gerenciadorviagem.dao.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class LoginUser(
    val email : String = "",
    val senha : String = "",
    val errorMessage : String = "",
    val isRegistred: Boolean = false,
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

class LoginrUserViewModel(private val userDao: UserDao) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUser())
    val uiState : StateFlow<LoginUser> = _uiState.asStateFlow()

    fun onEmailChange(email:String){
        _uiState.value = _uiState.value.copy(email = email)
    }
    fun onSenhaChange(senha:String){
        _uiState.value = _uiState.value.copy(senha = senha)

    }
    fun login() {
        viewModelScope.launch {
            try {
                _uiState.value.validateAllFields()

                val user = userDao.findByEmailPassword(_uiState.value.email, _uiState.value.senha)

                val contaExiste = user != null
                _uiState.value = _uiState.value.copy(
                    isRegistred = contaExiste,
                )
                println(contaExiste)

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = e.message ?: "Erro desconhecido"
                )
            }
        }
    }

    fun cleanErrorMessage() {
        _uiState.value = _uiState.value.copy(errorMessage = "")
    }
}