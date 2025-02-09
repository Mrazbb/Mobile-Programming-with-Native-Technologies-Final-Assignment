package com.example.finalassignment.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    Text(
        text = "Settings Screen",
        modifier = Modifier.padding(8.dp)
    )
}
