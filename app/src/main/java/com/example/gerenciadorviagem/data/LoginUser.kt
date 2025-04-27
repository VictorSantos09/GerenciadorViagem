package com.example.gerenciadorviagem.data

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