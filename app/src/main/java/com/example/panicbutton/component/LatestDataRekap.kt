package com.example.panicbutton.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
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
import com.example.panicbutton.viewmodel.RekapData
import com.example.panicbutton.viewmodel.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.panicbutton.R

@Composable
fun LatestDataRekap(
    modifier: Modifier = Modifier,
    viewModel: ViewModel = viewModel(),
    navController: NavController
) {
    val rekapData by viewModel.rekapData.observeAsState(emptyList())
    val errorMessage by viewModel.errorMessage.observeAsState("")

    LaunchedEffect(Unit) {
        viewModel.latestRekap()
    }

    Box(
        modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp),
        contentAlignment = Alignment.Center
    ) { when {
        rekapData.isNotEmpty() -> {
            LazyColumn() {
                items(rekapData) { log ->
                    RekapTable(log, viewModel, navController)
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
fun RekapTable(
    log: RekapData,
    viewModel: ViewModel = viewModel(),
    navController: NavController
) {

   Column(
       modifier = Modifier
           .clickable { viewModel.detailLog(log.nomor_rumah)
           navController.navigate("detail_log_screen/${log.nomor_rumah}")}
   ) {
       Row(
           modifier = Modifier
               .padding(vertical = 4.dp)
               .fillMaxWidth(),
           horizontalArrangement = Arrangement.SpaceBetween
       ){
           Text(
               text = log.nomor_rumah,
               fontSize = 16.sp,
               fontWeight = FontWeight.Bold,
               color = colorResource(id = R.color.font2)
           )
           Text(
               text = log.waktu,
               fontSize = 16.sp,
               fontWeight = FontWeight.Normal,
               color = colorResource(id = R.color.font2)
           )
           Text(
               text = log.status,
               fontSize = 16.sp,
               fontWeight = FontWeight.Normal,
               color = if (log.status  == "Online") Color.Green else colorResource(id = R.color.primary)
           )
       }
       Divider(
           color = Color.Gray,
           thickness = 0.5.dp
       )
   }
}