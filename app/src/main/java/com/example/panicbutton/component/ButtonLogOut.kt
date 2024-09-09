package com.example.panicbutton.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.panicbutton.R
import com.example.panicbutton.viewmodel.ViewModel

@Composable
fun ButtonLogOut(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    var showKeluarDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val viewModel = ViewModel()

    Column(
        modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { showKeluarDialog = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.biru),
                contentColor = Color.White
            )
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp),
                painter = painterResource(id = R.drawable.ic_logout),
                contentDescription = "ic_logout",
                tint = Color.Black)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Keluar",
                color = Color.Black)
        }
        if (showKeluarDialog) {
            AlertDialog(
                onDismissRequest = { showKeluarDialog},
                title = { Text( "Konfirmasi")},
                text = { Text("Apakah Anda yakin ingin keluar?")},
                confirmButton = {
                    Button(
                        onClick = {
                            showKeluarDialog = false
                            viewModel.logout(context, navController)
                        }
                    ) { Text("Ya")

                    }
                },
                dismissButton = {
                    Button(onClick = { showKeluarDialog = false }) {
                        Text("Tidak")
                    }
                }
            )
        }
    }
}