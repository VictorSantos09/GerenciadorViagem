package com.example.gerenciadorviagem.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gerenciadorviagem.R
import com.example.gerenciadorviagem.components.ErrorDialog
import com.example.gerenciadorviagem.components.MyPasswordField
import com.example.gerenciadorviagem.components.MyTextField
import com.example.gerenciadorviagem.data.RegisterUserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroScreen(onRegister:(String)->Unit,
                   backToLogin:()->Unit)
{
    val registerUserViewModel : RegisterUserViewModel = viewModel()
    var registerUser = registerUserViewModel.uiState.collectAsState()
    val ctx = LocalContext.current

    Column (verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(65.dp)) {

        Image(painter = painterResource(R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(200.dp))

        MyTextField(value = registerUser.value.email,
            onValueChange = {registerUserViewModel.onEmailChange(it)},
            "Email",
            true)

        MyPasswordField(value = registerUser.value.senha,
            onValueChange = {registerUserViewModel.onSenhaChange(it)},
            "Senha",
            true)

        MyPasswordField(value = registerUser.value.confirmarsenha,
            confirmValue = registerUser.value.senha,
            onValueChange = {registerUserViewModel.onConfirmarSenhaChange(it)},
            "Confirmar Senha",
            true)

        OutlinedButton(onClick = {
            if (registerUserViewModel.register()){
                Toast.makeText(ctx, "Conta criada com sucesso", Toast.LENGTH_SHORT).show()
            }
        },
            Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
                ) { Text(text = "Registrar") }

        if (registerUser.value.errorMessage.isNotBlank()){
            ErrorDialog(
                error = registerUser.value.errorMessage,
                onDismissRequest = {
                    registerUserViewModel.cleanErrorMessage()
                })
        }
        TextButton(onClick = backToLogin) { Text(text = "Entrar") }
    }
}