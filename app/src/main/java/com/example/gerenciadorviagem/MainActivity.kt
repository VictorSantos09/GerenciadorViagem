package com.example.gerenciadorviagem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gerenciadorviagem.screens.LoginScreen
import com.example.gerenciadorviagem.screens.MainScreen
import com.example.gerenciadorviagem.screens.CadastroScreen
import com.example.gerenciadorviagem.ui.theme.GerenciadorViagemTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            GerenciadorViagemTheme {
              Scaffold (modifier = Modifier.fillMaxSize()) {innerPadding ->
                  Column (Modifier.padding(innerPadding)) {
                      NavHost (
                          navController = navController,
                          startDestination = "LoginScreen"){

                          composable(route="LoginScreen"){
                              LoginScreen(onLogin={navController.navigate("MainScreen")}, onRegister =  {navController.navigate("RegisterScreen")})
                          }

                          composable(route="RegisterScreen"){
                              CadastroScreen (onRegister = {navController.navigate(it)}, backToLogin =  {navController.navigateUp()})
                          }
                          composable(route="MainScreen"){
                              MainScreen(onEditTrip = {}, onRegisterTrip = {})
                          }
//                            composable(route="ScreenC"){
//                            }

                      }
                  }
              }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GerenciadorViagemTheme {
        Greeting("Android")
    }
}