package com.example.panicbutton.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.panicbutton.component.ButtonLogOut
import com.example.panicbutton.component.Rekap5
import com.example.panicbutton.viewmodel.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun Dashboard(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ViewModel = viewModel()
) {
    Column(
        modifier
            .padding(top = 60.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ButtonLogOut(navController = navController)
        ScreenMonitor(navController = navController)
        Rekap5(modifier, viewModel)
        TextButton(
            onClick = { navController.navigate("data_rekap") }) {
            Text(
                text ="Lihat selengkapnya"
            )
        }
    }
}



