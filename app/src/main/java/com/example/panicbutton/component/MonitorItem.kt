package com.example.panicbutton.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
                .padding(horizontal = 24.dp)
                .clickable {
                    viewModel.detailLog(log.nomor_rumah)
                    navController.navigate("detail_log_screen/${log.nomor_rumah}")
                }
                .wrapContentHeight()
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
                    .padding(start = 24.dp, end = 24.dp, top = 28.dp, bottom = 20.dp),
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
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier
                        .fillMaxWidth()
                        .height(36.dp)
                        .background(
                            color = when (log.prioritas) {
                                "Darurat" -> colorResource(id = R.color.darurat)
                                "Penting" -> colorResource(id = R.color.penting)
                                else -> (colorResource(id = R.color.biasa))
                            },
                            RoundedCornerShape(10.dp)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = log.prioritas,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    Text(
                        text = "Pesan:",
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.font)
                    )
                    Text(
                        text = log.pesan,
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.font3),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(lineHeight = 20.sp),
                        textAlign = TextAlign.Justify
                    )
                }
            }
        }
    }
}
