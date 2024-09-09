package com.example.panicbutton.screen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.panicbutton.component.ButtonLogOut


@Composable
fun Dashboard(
    navController: NavController,
    modifier: Modifier = Modifier
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
        TextButton(
            onClick = { navController.navigate("data_rekap") }) {
            Text(
                text ="Lihat selengkapnya"
            )
        }
        Spacer(modifier.height(16.dp))
    }
}



