package com.example.panicbutton.component

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.panicbutton.viewmodel.PanicButton
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.panicbutton.R
import kotlinx.coroutines.delay

@Composable
fun ToggleSwitch(
    snackbarHostState : SnackbarHostState,
    viewModel: PanicButton = viewModel()
) {
    var isOn by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
    val nomorRumah = sharedPref.getString("nomorRumah", "")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
                    .padding(bottom = 16.dp)
            )
        }
        Switch(
            checked = isOn,
            onCheckedChange = { checked ->
                if (checked) {
                    showDialog = true
                } else {
                    isOn = false
                    isLoading = true
                    if (nomorRumah != null && nomorRumah.isNotEmpty()) {
                        viewModel.toggleDevice(isOn, nomorRumah, snackbarHostState) {
                            isLoading = false
                        }
                    } else {
                        Log.e("ToggleSwitch", "Nomor rumah is null or empty")
                        isLoading = false
                    }
                }
            },
            thumbContent = {
                if (isOn) {
                    Icon(
                        painter = painterResource(id = R.drawable.onmode),
                        contentDescription = "on mode",
                        modifier = Modifier
                            .padding(5.dp),
                        tint = Color.White
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.offmode),
                        contentDescription = "off mode",
                        modifier = Modifier
                            .padding(5.dp)
                            .size(24.dp),
                        tint = Color.White
                    )
                }
            },
            colors = SwitchDefaults.colors(
                checkedTrackColor = colorResource(id = R.color.pudar),
                uncheckedTrackColor = colorResource(id = R.color.merah_pudar),
                uncheckedBorderColor = colorResource(id = R.color.primary),
                checkedThumbColor = colorResource(id = R.color.biru),
                uncheckedThumbColor = colorResource(id = R.color.primary),
                checkedBorderColor = colorResource(id = R.color.biru)
            ),
            modifier = Modifier
                .scale(1.8f)
                .padding(20.dp),
            enabled = !isLoading
        )
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Konfirmasi") },
            text = { Text("Apakah Anda yakin ingin mengaktifkan Panic Button?") },
            confirmButton = {
                Button(
                    onClick = {
                        isOn = true
                        isLoading = true
                        showDialog = false
                        if (nomorRumah != null && nomorRumah.isNotEmpty()) {
                            viewModel.toggleDevice(isOn, nomorRumah, snackbarHostState) {
                                isLoading = false
                            }
                        } else {
                            Log.e("ToggleSwitch", "Nomor rumah is null or empty")
                            isLoading = false
                        }
                    }
                ) {
                    Text("Ya")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Tidak")
                }
            }
        )
    }

    LaunchedEffect(key1 = isOn) {
        if (isOn) {
            delay(15000)
            isOn = false
            isLoading = true
            if (nomorRumah != null && nomorRumah.isNotEmpty()) {
                viewModel.toggleDevice(isOn, nomorRumah, snackbarHostState) {
                    isLoading = false
                }
            } else {
                Log.e("toggleSwitch", "Nomor rumah is null or empty")
                isLoading = false
            }
        }
    }
}