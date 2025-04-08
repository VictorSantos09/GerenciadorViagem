package com.example.gerenciadorviagem

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gerenciadorviagem.screens.CadastroUsuarioMainScreen
import com.example.gerenciadorviagem.screens.LoginScreen
import com.example.gerenciadorviagem.screens.MainScreen
import com.example.gerenciadorviagem.ui.theme.GerenciadorViagemTheme
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val currentBackStackEntry = navController.currentBackStackEntryFlow.collectAsState(initial = null)
            GerenciadorViagemTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    
                    topBar = {
                        if (currentBackStackEntry.value?.destination?.route != "LoginScreen" && currentBackStackEntry.value?.destination?.route != "CadastroScreen")
                        {
                            TopAppBar(title = { Text("Bem-vindo",
                                        fontWeight = FontWeight.W900,
                                        fontSize = 25.sp,
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center) },
                                
                        colors = TopAppBarDefaults.topAppBarColors(titleContentColor = Color.Blue, containerColor = Color.Transparent))
                        }},
                    
                    bottomBar = {
                            if (currentBackStackEntry.value?.destination?.route != "LoginScreen" && currentBackStackEntry.value?.destination?.route != "CadastroScreen") {
                            BottomNavigation (backgroundColor = Color.Blue) {
                            val backStack = navController.currentBackStackEntryAsState()
                            val currentDestination = backStack.value?.destination
                            
                                BottomNavigationItem(
                                selected =
                                currentDestination?.hierarchy?.any {
                                    it.route == "MainScreen"
                                } == true,
                                onClick = { navController.navigate("MainScreen") },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Home,
                                        contentDescription = "Tela principal",
                                        tint = Color.White
                                    )
                                }
                            )

                                BottomNavigationItem(
                                selected =
                                currentDestination?.hierarchy?.any {
                                    it.route == "TravelForm"
                                } == true,
                                onClick = { navController.navigate("TravelForm") },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Nova viagem",
                                        tint = Color.White
                                    )
                                }
                            )
                            
                                BottomNavigationItem(
                                selected =
                                currentDestination?.hierarchy?.any {
                                    it.route == "About"
                                } == true,
                                onClick = { navController.navigate("About") },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Info,
                                        contentDescription = "About",
                                        tint = Color.White
                                    )
                                }
                            )
                        }
                        }
                    }
                    )
                { innerPadding ->
                    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
                    Column(Modifier.padding(innerPadding)) {
                        NavHost(
                            navController = navController,
                            startDestination = "LoginScreen"
                        ) {

                            composable(route = "LoginScreen") {
                                LoginScreen(
                                    onLogin = { navController.navigate("MainScreen") },
                                    onRegister = { navController.navigate("CadastroScreen") })
                            }

                            composable(route = "CadastroScreen") {
                                CadastroUsuarioMainScreen(backToLogin = { navController.navigateUp() })
                            }
                            composable(route = "MainScreen") {
                                MainScreen(onEditTrip = {}, onRegisterTrip = {})
                            }
                        }
                    }
                }
            }
        }
    }
}






