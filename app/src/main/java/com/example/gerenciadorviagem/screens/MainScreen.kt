package com.example.gerenciadorviagem.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gerenciadorviagem.database.AppDatabase
import com.example.gerenciadorviagem.factory.NewTripViewModelFactory
import com.example.gerenciadorviagem.viewmodel.NewTripViewModel
import com.example.gerenciadorviagem.entity.Trip
import com.example.gerenciadorviagem.shared.Routes

@Composable
fun MainScreen(navController: NavHostController) {
    val context = LocalContext.current
    val tripDao = AppDatabase.getDatabase(context).tripDao()
    val factory = NewTripViewModelFactory(tripDao)
    val viewModel: NewTripViewModel = viewModel(factory = factory)
    val tripList by viewModel.tripList.collectAsState()

    Scaffold {
        DisplayTrips(tripList, navController, it)
    }
}

@Composable
fun DisplayTrips(tripList: List<Trip>, navController: NavHostController, it: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .padding(it)
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(tripList) { trip ->
            TripCard(trip = trip) {
                navController.navigate("${Routes.VIAGEM}/${trip.id}")
            }
        }
    }
}