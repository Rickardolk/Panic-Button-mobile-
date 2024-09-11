package com.example.panicbutton.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panicbutton.R
import com.example.panicbutton.viewmodel.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun MonitorItem(
    modifier: Modifier = Modifier,
    viewModel: ViewModel,
    navController: NavController
) {
    val monitoringData by viewModel.monitoringData.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        while (true) {
            viewModel.monitoring()
            delay(2000)
        }

    }

    monitoringData.forEach { log ->
        Column(
            modifier
                .clickable {
                    viewModel.detailLog(log.nomor_rumah)
                    navController.navigate("detail_log_screen/${log.nomor_rumah}")},
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Card(
                modifier
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier
                        .background(color = colorResource(id = R.color.merah_pudar), RoundedCornerShape(16.dp))
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Nomor Rumah",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = log.nomor_rumah,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = log.waktu,
                        fontSize = 14.sp
                    )
                }
            }

        }
    }

}
