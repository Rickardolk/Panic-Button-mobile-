package com.example.panicbutton.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.panicbutton.viewmodel.RekapData
import com.example.panicbutton.viewmodel.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LatestDataRekap(
    modifier: Modifier = Modifier,
    viewModel: ViewModel = viewModel()
) {
    val rekapData by viewModel.rekapData.observeAsState(emptyList())
    val errorMessage by viewModel.errorMessage.observeAsState("")

    LaunchedEffect(Unit) {
        viewModel.rekap5()
    }

    Box(
        modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp),
        contentAlignment = Alignment.Center
    ) { when {
        rekapData.isNotEmpty() -> {
            LazyColumn(
                modifier
                    .padding(16.dp)
            ) {
                items(rekapData) { log ->
                    RekapLogRow(log)
                }
            }
        }
        errorMessage.isNotEmpty() -> {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Center)
            )
        } else -> {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )}
        }
    }
}

@Composable
fun RekapLogRow(log: RekapData) {

   Row() {
       Text(
           text = log.nomor_rumah,
           modifier = Modifier.weight(1f),
           style = MaterialTheme.typography.bodyMedium
           )
       Text(
           text = log.waktu,
           modifier = Modifier.weight(1f),
           style = MaterialTheme.typography.bodyMedium
       )
       Text(
           text = log.status,
           modifier = Modifier.weight(1f),
           style = MaterialTheme.typography.bodyMedium
       )
   }
}