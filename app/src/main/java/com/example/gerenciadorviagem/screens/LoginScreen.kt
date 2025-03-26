package com.example.gerenciadorviagem.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gerenciadorviagem.components.ErrorDialog
import com.example.gerenciadorviagem.components.MyPasswordField
import com.example.gerenciadorviagem.components.MyTextField
import com.example.gerenciadorviagem.data.LoginrUserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLogin:()->Unit,
    onRegister:()->Unit){

    val loginUserViewModel : LoginrUserViewModel = viewModel()
    var loginUser = loginUserViewModel.uiState.collectAsState()

    Column (verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(65.dp)) {
        MyTextField(value=loginUser.value.login, onValueChange = {loginUserViewModel.onLoginChange(it)}, "Login", false)
        MyPasswordField(value=loginUser.value.senha, onValueChange = {loginUserViewModel.onSenhaChange(it)}, "Senha", false)
        OutlinedButton(onClick = {
            if (loginUserViewModel.login()){
                onLogin()
            }
        },
            Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
                .height(60.dp), shape = RoundedCornerShape(5.dp), colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White, contentColor = Color.DarkGray)) { Text(text = "Login") }

        if (loginUser.value.errorMessage.isNotBlank()){
            ErrorDialog(
                error = loginUser.value.errorMessage,
                onDismissRequest = {
                    loginUserViewModel.cleanErrorMessage()
                })
        }

        OutlinedButton(onClick = onRegister,
            Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
                .height(60.dp), shape = RoundedCornerShape(5.dp), colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White, contentColor = Color.DarkGray)) { Text(text = "Registrar-se") }
    }

}
