package com.example.panicbutton.component.displayData

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panicbutton.R
import com.example.panicbutton.viewmodel.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun KeteranganUser(
    modifier: Modifier = Modifier,
    viewModel: ViewModel = viewModel(),
    context: Context
) {
    val sharedPref = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
    val nomorRumah = sharedPref.getString("nomorRumah", "norum tidak ada")
    val defaultText = stringResource(id = R.string.Keterangan)
    val textScroll = rememberScrollState()
    val keteranganUser by viewModel.keterangan.observeAsState(defaultText)
    var userText by remember { mutableStateOf(defaultText)}
    var isEditing by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var editedText by remember { mutableStateOf(userText) }
    var isTextChanges by remember { mutableStateOf(false) }

    LaunchedEffect(nomorRumah) {
        viewModel.getKeteranganUser(nomorRumah.toString())
    }

    Box(
        modifier
            .height(130.dp)
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Text(
            modifier = Modifier
                .fillMaxHeight()
                .padding(24.dp)
                .verticalScroll(textScroll),
            text = keteranganUser,
            fontSize = 12.sp,
            style = TextStyle(lineHeight = 16.sp)
        )
        Column(
            modifier
                .height(80.dp)
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.White
                        )
                    )
                )
                .align(Alignment.BottomCenter)
                .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    modifier = Modifier
                        .wrapContentSize(),
                    onClick = {
                        showDialog = true
                        isEditing = false
                        editedText = userText
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.ic_back_color)
                    )
                ) {
                    Text(
                        text = "Tambahkan",
                        color = colorResource(id = R.color.font2)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_right),
                        contentDescription = "ic_arrow_right",
                        tint = colorResource(id = R.color.font2)
                    )
                }
            }
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "Edit Keterangan")},
                text = {
                    OutlinedTextField(
                        value = editedText,
                        onValueChange = { newText ->
                            editedText = newText
                            isTextChanges = newText != userText
                        },
                        label = { Text("Keterangan")},
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                confirmButton = {
                    if (isTextChanges) {
                        Button(
                            onClick = {
                                userText = editedText
                                isTextChanges = false
                                isEditing = false
                                showDialog = false
                                if (nomorRumah != null) {
                                    Log.d("UpdateKeterangan", "Nomor Rumah: $nomorRumah, Keterangan: $userText")
                                    viewModel.updateKeterangan(nomorRumah, userText)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.primary)
                            )
                        ) {
                            Text("Selesai",
                                color = Color.White
                            )
                        }
                    } else {
                        Button(
                            onClick = {
                                isEditing = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.primary)
                            )
                        ) {
                            Text("Edit")
                        }
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDialog = false
                            isEditing = false
                            isTextChanges = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.alert_kembali_button)
                        )
                    ) {
                        Text("Batal",
                            color = colorResource(id = R.color.alert_kembali_text))
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Liat() {
    KeteranganUser(context = LocalContext.current)
}