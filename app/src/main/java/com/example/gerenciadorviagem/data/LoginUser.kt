package com.example.gerenciadorviagem.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gerenciadorviagem.dao.UserDao
import com.example.gerenciadorviagem.entity.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUser(
    val email : String = "",
    val senha : String = "",
    val errorMessage : String = "",
    val logado: Boolean = false
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

class LoginUserViewModel(private val userDao: UserDao) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUser())
    val uiState : StateFlow<LoginUser> = _uiState.asStateFlow()

    fun onEmailChange(email:String){
        _uiState.value = _uiState.value.copy(email = email)
    }
    fun onSenhaChange(senha:String){
        _uiState.value = _uiState.value.copy(senha = senha)

    }
    fun login(login: String, senha: String) {
         try {
            _uiState.value.validateAllFields()

            viewModelScope.launch {
                val u: User? = userDao.findByEmail(login)
                try {
                    if (u != null){
                        if (u.password == senha){
                            _uiState.value = _uiState.value.copy(logado = true)
                        }
                        else{
                            throw Exception("Login ou senha inválidos")
                        }
                    }
                    else{
                        throw Exception("Login ou senha inválidos")
                    }
                }
                catch (e: Exception){
                    _uiState.value = _uiState.value.copy(errorMessage = e.message ?: "Unknown error")
                }
            }
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