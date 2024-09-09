package com.example.panicbutton.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panicbutton.viewmodel.ViewModel

@Composable
fun DetailLogScreen(
    viewModel: ViewModel,
    nomorRumah: String,
    modifier: Modifier = Modifier
) {
    val detailLogData by viewModel.detailLogData.observeAsState(emptyList())

    LaunchedEffect(nomorRumah) {
        viewModel.detailLog(nomorRumah)
    }

    Column(
        modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text =" Detail Log for $nomorRumah",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        if (detailLogData.isEmpty()) {
            Text(text = "No log data available.")
        } else {
            detailLogData.forEach { log ->
                Column(
                    modifier
                        .background(Color.LightGray, RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ){
                  Text(text = "Nomor Rumah ${log.nomor_rumah}")  
                  Text(text = "Waktu ${log.waktu}")
                  Text(text = "Status ${log.status}")
                }
            }
        }
    }
}