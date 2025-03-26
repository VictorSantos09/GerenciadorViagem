package com.example.gerenciadorviagem.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onRegisterTrip:()->Unit,
    onEditTrip:()->Unit,){
    Scaffold (topBar = { TopAppBar(title = { Text("Texto") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,titleContentColor = Color.Black)) })
    { innerPadding ->
        Column (Modifier.padding(innerPadding)) {

        }
    }


}
