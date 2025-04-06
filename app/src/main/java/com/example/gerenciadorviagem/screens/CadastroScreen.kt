package com.example.gerenciadorviagem.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gerenciadorviagem.R
import com.example.gerenciadorviagem.components.ErrorDialog
import com.example.gerenciadorviagem.components.MyPasswordField
import com.example.gerenciadorviagem.components.MyTextField
import com.example.gerenciadorviagem.data.RegisterUserViewModel
import com.example.gerenciadorviagem.ui.theme.GerenciadorViagemTheme
import com.example.registeruser.database.AppDatabase

@Composable
fun CadastroUsuarioMainScreen(backToLogin:() -> Unit) {
    val ctx = LocalContext.current
    val userDao = AppDatabase.getDatabase(ctx).userDao()

    val registerUserViewModel : RegisterUserViewModel =
        viewModel(
            factory =
                RegisterUserViewModelFactory(userDao)
        )

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Image(painter = painterResource(R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(200.dp))

            RegisterUserFields(registerUserViewModel, backToLogin)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterUserFields(registerUserViewModel: RegisterUserViewModel, backToLogin: () -> Unit) {
    var registerUser = registerUserViewModel.uiState.collectAsState()
    val ctx = LocalContext.current

    MyTextField(
        label = "Nome",
        value = registerUser.value.user,
        onValueChange = {
            registerUserViewModel.onUserChange(it)
        },
    )
    MyTextField(
        label = "Email",
        value = registerUser.value.email,
        onValueChange = {
            registerUserViewModel.onEmailChange(it)
        },
    )
    MyPasswordField(
        label = "Senha",
        value = registerUser.value.senha,
        errorMessage = registerUser.value.validatePassord(),
        onValueChange = {
            registerUserViewModel.onSenhaChange(it)
        })
    MyPasswordField(
        label = "Confirmar senha",
        value = registerUser.value.confirmarSenha,
        errorMessage = registerUser.value.validateConfirmPassword() ,
        onValueChange = {
            registerUserViewModel.onConfirmarSenhaChange(it)
        })



    Button(
        modifier = Modifier.padding(top = 16.dp),
        onClick = {
            registerUserViewModel.register()
        }
    ) {
        Text(text = "Criar conta")
    }

    Button(
        modifier = Modifier.padding(top = 16.dp),
        onClick = backToLogin
    ) {
        Text(text = "Já tenho conta")
    }

    if (registerUser.value.errorMessage.isNotBlank()) {
        ErrorDialog(
            error = registerUser.value.errorMessage,
            onDismissRequest =  {
                registerUserViewModel.cleanDisplayValues()
            },
        )
    }
    LaunchedEffect (registerUser.value.isSaved) {
        if (registerUser.value.isSaved) {
            Toast.makeText(ctx, "Conta criada com sucesso",
                Toast.LENGTH_SHORT).show()
            registerUserViewModel.cleanDisplayValues()
        }
    }
}