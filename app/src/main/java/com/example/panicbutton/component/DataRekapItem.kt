package com.example.panicbutton.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panicbutton.viewmodel.RekapData
import com.example.panicbutton.viewmodel.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun DataRekapItem(
    modifier: Modifier,
    data: RekapData,
    viewModel: ViewModel = viewModel(),
    navController: NavController
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(vertical = 8.dp, horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier
                .clickable {
                    viewModel.detailLog(data.nomor_rumah)
                    navController.navigate("detail_log_screen/${data.nomor_rumah}")
                }
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White),
            elevation = CardDefaults.cardElevation(3.dp)
        ) {
            Column(
                modifier
                    .padding(vertical = 16.dp, horizontal = 16.dp)
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
    }
}

@Preview(showBackground = true)
@Composable
private fun DataRekapItemPrev() {
    val sampleData = RekapData(
        nomor_rumah = "101",
        waktu = "12:00 PM",
        status = "Online",
        id = 1
    )

    // Menggunakan NavController untuk preview
    val navController = rememberNavController()

    DataRekapItem(
        modifier = Modifier,
        data = sampleData,
        navController = navController
    )
}