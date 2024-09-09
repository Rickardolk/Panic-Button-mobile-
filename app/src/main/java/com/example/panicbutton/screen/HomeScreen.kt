package com.example.panicbutton.screen

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.panicbutton.component.ButtonLogOut
import com.example.panicbutton.component.ToggleSwitch
import com.example.panicbutton.viewmodel.ViewModel

@Composable fun HomeScreen(
    modifier: Modifier,
    snackbarHostState: SnackbarHostState,
    navController: NavController,
    context: Context
) {
    val sharedPref = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
    val nomorRumah = sharedPref.getString("nomorRumah", "Nomor rumah tidak ditemukan")

    Column(
        modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier
                .fillMaxWidth()
                .padding(bottom = 100.dp, end = 24.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            ButtonLogOut(
                navController = navController
            )
        }
        Text(
            text = "Nomor Rumah: $nomorRumah",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Spacer(modifier.height(16.dp))
        Text(
            text = "Panic Button",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        ToggleSwitch(
            snackbarHostState = snackbarHostState
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun liat() {
    HomeScreen(
        modifier = Modifier,
        snackbarHostState = SnackbarHostState(),
        navController = rememberNavController(),
        context = LocalContext.current
    )
}
