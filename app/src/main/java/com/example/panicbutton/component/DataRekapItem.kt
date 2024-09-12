package com.example.panicbutton.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panicbutton.viewmodel.RekapData
import com.example.panicbutton.viewmodel.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun DataRekapItem(
    modifier: Modifier,
    data: RekapData,
    viewModel: ViewModel = viewModel(),
    navController: NavController
) {
    Column(
        modifier
            .clickable {viewModel.detailLog(data.nomor_rumah)
            navController.navigate("detail_log_screen/${data.nomor_rumah}")}
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "Nomor Rumah: ${data.nomor_rumah}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Waktu: ${data.waktu}",
            fontSize = 14.sp
        )
        Text(
            text = "Status: ${data.status}",
            fontSize = 14.sp,
            color = if (data.status == "Online") Color.Green else Color.Red
        )
    }
}