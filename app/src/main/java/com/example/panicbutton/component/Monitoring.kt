package com.example.panicbutton.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panicbutton.R
import com.example.panicbutton.viewmodel.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Monitoring(
    modifier: Modifier = Modifier,
    viewModel: ViewModel
) {
    val monitoringData by viewModel.monitoringData.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.monitoring()
    }

    Column(
        modifier
            .background(color = colorResource(id = R.color.merah_pudar), RoundedCornerShape(16.dp))
            .padding(top = 16.dp, bottom = 16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text =" Nomor Rumah",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        monitoringData.forEach { log ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
