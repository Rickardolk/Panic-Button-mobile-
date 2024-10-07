package com.example.panicbutton.component.button

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.panicbutton.R
import com.example.panicbutton.notiification.sendNotification
import com.example.panicbutton.viewmodel.ViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToggleSwitch(
    viewModel: ViewModel = viewModel()
) {
    var isOn by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var userInput by remember { mutableStateOf("") }
    var selectedPriority by remember {mutableStateOf("")}
    var showError by remember {mutableStateOf(false)}
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
        LaunchedEffect(key1 = isOn) {
            if (isOn) {
                delay(15000)
                isOn = false
                if (!nomorRumah.isNullOrEmpty()) {
                    viewModel.toggleDevice(context,isOn, nomorRumah,"","")
                } else {
                    Log.e("toggleSwitch", "Nomor rumah is null or empty")
                }
            }
        }

        Switch(
            checked = isOn,
            onCheckedChange = { checked ->
                if (checked) {
                    showDialog = true
                } else {
                    isOn = false
                    if (!nomorRumah.isNullOrEmpty()) {
                        viewModel.toggleDevice(context, isOn, nomorRumah,"", "")
                    } else {
                        Log.e("ToggleSwitch", "Nomor rumah is null or empty")
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
                .padding(20.dp)
        )
    }
    if (showDialog) {
        userInput = ""
        selectedPriority = ""
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_warning),
                    contentDescription = "ic warning",
                    tint = colorResource(id = R.color.darurat)
                )
                Text(
                    text = "Konfirmasi",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(id = R.color.primary)
                )
            } },
            text = {
                Column {
                    Text("Tambahkan Pesan dan Prioritas")
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = userInput,
                        onValueChange = { userInput = it },
                        placeholder = { Text( "Masukkan pesan")},
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = colorResource(id = R.color.font),
                            focusedLabelColor = colorResource(id = R.color.font),
                            cursorColor = colorResource(id = R.color.font)
                        )
                    )
                    PriorityButton(
                        onPrioritySelected = { priority ->
                            selectedPriority = priority
                        }
                    )
                    if (showError) {
                        Text(
                            text = "Silahkan pilih tombol prioritas",
                            color = colorResource(id = R.color.darurat),
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            },
            containerColor = Color.White,
            confirmButton = {
                Button(
                    modifier = Modifier
                        .width(130.dp),
                    onClick = {
                        if (selectedPriority.isEmpty()) {
                            showError = true
                        } else {
                            showError = false
                            isOn = true
                            showDialog = false
                            if (!nomorRumah.isNullOrEmpty()) {
                                viewModel.toggleDevice(
                                    context,
                                    isOn,
                                    nomorRumah,
                                    userInput,
                                    selectedPriority
                                )
                                sendNotification(
                                    context = context,
                                    title = "Panic Button",
                                    message = "Buzzer telah di bunyikan oleh nomor rumah $nomorRumah dengan prioritas $selectedPriority, pesan: $userInput"
                                )
                            } else {
                                Log.e("ToggleSwitch", "Nomor rumah is null or empty")
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.primary)
                    )
                ) {
                    Text(
                        "Kirim",
                        color = Color.White
                    )
                }
            },
            dismissButton = {
                Button(
                    modifier = Modifier
                        .width(130.dp),
                    onClick = { showDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.alert_kembali_button)
                    )
                ) {
                    Text(
                        "Tidak",
                        color = colorResource(id = R.color.alert_kembali_text)
                    )
                }
            }
        )
    }
}


