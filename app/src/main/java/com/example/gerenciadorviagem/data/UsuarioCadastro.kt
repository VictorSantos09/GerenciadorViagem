package com.example.gerenciadorviagem.data

import com.example.gerenciadorviagem.entity.User

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