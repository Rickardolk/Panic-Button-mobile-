package com.example.panicbutton.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        Card(
            modifier
                .clickable { viewModel.detailLog(log.nomor_rumah)
                navController.navigate("detail_log_screen/${log.nomor_rumah}")}
                .padding(start = 24.dp, end = 24.dp)
                .height(200.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.elevatedCardElevation(4.dp),
            colors = CardDefaults.cardColors(
                contentColor = Color.White,
                containerColor = Color.White
            ),

            ) {
            Column(
                modifier
                    .fillMaxWidth()
                    .padding(start = 56.dp, end = 56.dp, top = 29.dp, bottom = 29.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "NOMOR RUMAH",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.font2)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = log.nomor_rumah,
                    fontSize = 64.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.font2)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = log.waktu,
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.font2)
                )
            }
        }
    }
}
