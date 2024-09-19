package com.example.panicbutton.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panicbutton.viewmodel.RekapData
import com.example.panicbutton.viewmodel.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.panicbutton.R
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun LatestDataRekap(
    modifier: Modifier = Modifier,
    viewModel: ViewModel = viewModel(),
    navController: NavController
) {
    val scope = rememberCoroutineScope()
    val dragOffset = remember { Animatable(0f) }
    val dragThreshold = with(LocalDensity.current) {200.dp.toPx()}
    val rekapData by viewModel.rekapData.observeAsState(emptyList())
    val errorMessage by viewModel.errorMessage.observeAsState("")

    LaunchedEffect(Unit) {
        viewModel.latestRekap()
    }
    Column(
        modifier
            .fillMaxSize()
            .offset { IntOffset(0, dragOffset.value.roundToInt()) }
            .background(
                color = Color.White,
                RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp)
            )
            .pointerInput(Unit) {
                detectVerticalDragGestures(
                    onVerticalDrag = { _, dragAmount ->
                        scope.launch {
                            dragOffset.snapTo(dragOffset.value + dragAmount)
                        }
                    },
                    onDragEnd = {
                        scope.launch {
                            if (dragOffset.value < -dragThreshold) {
                                dragOffset.animateTo(-1000f, animationSpec = tween(300))
                                navController.navigate("data_rekap")
                            } else {
                                dragOffset.animateTo(0f, animationSpec = tween(300))
                            }
                        }
                    }
                )
            }
    ) {
        Row(
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, top = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Data Rekap",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.font)
            )
            TextButton(
                onClick = {
                    navController.navigate("data_rekap")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = colorResource(id = R.color.font2)
                )
            ) {
                Text(
                    text = "Lihat Selengkapnya",
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier
                .fillMaxSize()
                .background(
                    color = Color.White,
                    RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                )
                .padding(start = 24.dp, end = 24.dp),
            contentAlignment = Alignment.Center
        ) { when {
            rekapData.isNotEmpty() -> {
                LazyColumn {
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
}

@Composable
fun RekapTable(
    log: RekapData,
    viewModel: ViewModel = viewModel(),
    navController: NavController
) {

   Column(
       modifier = Modifier
           .fillMaxSize()
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
       HorizontalDivider(
           color = Color.Gray,
           thickness = 0.5.dp
       )
   }
}

@Preview(showBackground = false)
@Composable
private fun PreviewLatestDataRekap() {
    // Buat NavController palsu untuk preview
    val navController = rememberNavController()
    // Buat ViewModel palsu untuk preview
    val viewModel = viewModel<ViewModel>().apply {
        // Jika perlu, tambahkan data contoh di sini
    }

    // Panggil composable yang ingin di-preview
    LatestDataRekap(
        navController = navController,
        viewModel = viewModel
    )
}
