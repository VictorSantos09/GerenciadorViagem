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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gerenciadorviagem.screens.CadastroUsuarioMainScreen
import com.example.gerenciadorviagem.screens.LoginScreen
import com.example.gerenciadorviagem.screens.MainScreen
import com.example.gerenciadorviagem.screens.NewTripScreen
import com.example.gerenciadorviagem.shared.Routes
import com.example.gerenciadorviagem.ui.theme.GerenciadorViagemTheme
import com.example.gerenciadorviagem.features.itnerary.ItinerarySuggestionScreen

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
                        TopBar(currentBackStackEntry)
                    },
                    bottomBar = {
                        BottomBar(currentBackStackEntry, navController)
                    }
                )
                { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        ConfigurarMenus(navController)
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ConfigurarMenus(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(route = Routes.LOGIN) {
            LoginScreen(
                onLogin = { navController.navigate(Routes.TELA_PRINCIPAL) },
                onRegister = { navController.navigate(Routes.CADASTRO) })
        }

        composable(route = Routes.CADASTRO) {
            CadastroUsuarioMainScreen(backToLogin = { navController.navigateUp() })
        }
        composable(route = Routes.TELA_PRINCIPAL) {
            MainScreen(navController = navController)
        }

        composable(route = Routes.VIAGEM) {
            NewTripScreen(
                onNavigateTo = { navController.navigate(it) },
                id = null
            )
        }
        composable(
            route = "${Routes.VIAGEM}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType})) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            NewTripScreen(onNavigateTo = { navController.navigate(it) }, id = id)
        }

        composable("itinerary/{tripId}") { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId")?.toIntOrNull()
            if (tripId != null) {
                ItinerarySuggestionScreen(
                    tripId = tripId,
                    onNavigateTo = { navController.navigate(it) }
                )
            }
        }
    }
}
@Composable
fun BottomBar(currentBackStackEntry: State<NavBackStackEntry?>, navController: NavHostController){
    if (currentBackStackEntry.value?.destination?.route != Routes.LOGIN && currentBackStackEntry.value?.destination?.route != Routes.CADASTRO) {
        BottomNavigation (backgroundColor = Color.Blue) {
            val backStack = navController.currentBackStackEntryAsState()
            val currentDestination = backStack.value?.destination

            BottomNavigationItem(
                selected =
                currentDestination?.hierarchy?.any {
                    it.route == Routes.TELA_PRINCIPAL
                } == true,
                onClick = { navController.navigate(Routes.TELA_PRINCIPAL) },
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
                     it.route == Routes.VIAGEM
                 } == true,
                 onClick = { navController.navigate(Routes.VIAGEM) },
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
                    it.route == Routes.ABOUT
                } == true,
                onClick = { navController.navigate(Routes.ABOUT) },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(currentBackStackEntry: State<NavBackStackEntry?>){
    if (currentBackStackEntry.value?.destination?.route != Routes.LOGIN && currentBackStackEntry.value?.destination?.route != Routes.CADASTRO)
    {
        TopAppBar(title = { Text("Bem-vindo",
            fontWeight = FontWeight.W900,
            fontSize = 25.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center) },
            colors = TopAppBarDefaults.topAppBarColors(titleContentColor = Color.Blue, containerColor = Color.Transparent))
    }
}




