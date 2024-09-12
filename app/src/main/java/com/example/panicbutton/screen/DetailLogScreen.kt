package com.example.panicbutton.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DetailLogScreen(
    modifier: Modifier = Modifier,
    viewModel: ViewModel = viewModel(),
    nomorRumah: String
) {
    val detailLogData by viewModel.detailLogData.observeAsState(emptyList())

    LaunchedEffect(nomorRumah) {
        viewModel.detailLog(nomorRumah)
    }

    Box(
        modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(color = colorResource(id = R.color.primary))
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Detail Log",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Column(
            modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier
                    .padding(top = 140.dp)
                    .fillMaxWidth()
                    .height(78.dp)
                    .padding(start = 24.dp, end = 24.dp),
                colors = CardDefaults.cardColors(
                    contentColor = Color.White,
                    containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(
                    modifier
                        .fillMaxSize()
                        .padding(start = 34.dp, end = 34.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Nomor Rumah :",
                        fontSize = 16.sp,
                        color = colorResource(id = R.color.font2)
                    )
                    Text(
                        text = "$nomorRumah",
                        fontSize = 20.sp,
                        color = colorResource(id = R.color.font2),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier
                    .padding(26.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row (
                    modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "Waktu",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = colorResource(id = R.color.font)
                    )
                    Text(
                        text = "Status",
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = colorResource(id = R.color.font)
                    )
                }
                if (detailLogData.isEmpty()) {
                    Text(text = "No log data available.")
                } else {
                    detailLogData.forEach { log ->
                        Column {
                            Row(
                                modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                Text(
                                    text = "${log.waktu}",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = colorResource(id = R.color.font2)
                                )
                                Text(
                                    text = "${log.status}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = if (log.status == "Online") Color.Green else Color.Red
                                )
                            }
                            Divider(
                                color = Color.Gray,
                                thickness = 0.5.dp
                            )
                        }
                    }
                }
            }
        }
    }
}







