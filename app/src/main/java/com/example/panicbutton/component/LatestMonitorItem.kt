package com.example.panicbutton.component


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
fun LatestMonitorItem(
    modifier: Modifier = Modifier,
    viewModel: ViewModel,
    navController: NavController
) {
    val monitoringData by viewModel.latestMonitor.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        while (true) {
            viewModel.monitorLatest()
            delay(2000)
        }

    }
    Column(
        modifier
            .padding(start = 24.dp, end = 24.dp)
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(top = 16.dp, bottom = 16.dp)
    ){
        monitoringData.forEach { log ->
            Column(
                modifier
                    .clickable {
                        viewModel.detailLog(log.nomor_rumah)
                        navController.navigate("detail_log_screen/${log.nomor_rumah}")
                    },
            ) {
                Row(
                    modifier
                        .padding(start = 26.dp, end = 26.dp)
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = log.nomor_rumah,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = log.waktu,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
                Divider(
                    color = Color.Gray,
                    thickness = 0.5.dp,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}
