package com.example.gerenciadorviagem.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.gerenciadorviagem.components.ErrorDialog
import com.example.gerenciadorviagem.components.MyPasswordField
import com.example.gerenciadorviagem.components.MyTextField
import com.example.gerenciadorviagem.data.LoginrUserViewModel
import com.example.gerenciadorviagem.R
import com.example.registeruser.database.AppDatabase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLogin:()->Unit,
    onRegister:()->Unit){

    val ctx = LocalContext.current
    val userDao = AppDatabase.getDatabase(ctx).userDao()

    val loginUserViewModel : LoginrUserViewModel = viewModel(factory = LoginUserViewModelFactory(userDao))
    var loginUser = loginUserViewModel.uiState.collectAsState()

    Column (verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(65.dp)) {

        Image(painter = painterResource(R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(200.dp))

        MyTextField(value = loginUser.value.email,
                    onValueChange = {loginUserViewModel.onEmailChange(it)}
                    , label = "Email")

        MyPasswordField(value = loginUser.value.senha,
                    onValueChange = {loginUserViewModel.onSenhaChange(it)},
            label = "Senha")

        OutlinedButton(onClick = {
            loginUserViewModel.login()
            println("hit")
        },
            Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
                ) { Text(text = "Entrar") }

        OutlinedButton(onClick = onRegister,
            Modifier
                .fillMaxWidth())
        { Text(text = "Crie sua conta") }

        if (loginUser.value.errorMessage.isNotBlank()){
            ErrorDialog(
                error = loginUser.value.errorMessage,
                onDismissRequest = {
                    loginUserViewModel.cleanErrorMessage()
                })
        }

        LaunchedEffect (loginUser.value.isRegistred) {
            println("hit 1")
            if (!loginUser.value.isRegistred) {
                Toast.makeText(ctx, "Email ou senha inválidos",
                    Toast.LENGTH_SHORT).show()
            }else{
                onLogin()
            }
        }
    }
}
