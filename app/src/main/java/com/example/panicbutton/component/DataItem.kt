package com.example.panicbutton.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panicbutton.viewmodel.RekapData

@Composable
fun DataItem(
    modifier: Modifier,
    data: RekapData
) {
    Column(
        modifier
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