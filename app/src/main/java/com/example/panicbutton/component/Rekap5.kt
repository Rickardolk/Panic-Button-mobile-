package com.example.panicbutton.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.panicbutton.viewmodel.RekapData

@Composable
fun Rekap5(
    modifier: Modifier = Modifier,
    data: RekapData
) {
    Column(
        modifier
            .padding(start = 24.dp, end = 24.dp)
    ) {
        Text(
            text = "Data Rekap",
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .padding(16.dp)
        )
        LazyColumn {
            item (data) {
                Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = data.nomor_rumah)
                    Text(text = data.waktu)
                    Text(text = data.status)
                }
            }
        }
    }
}
