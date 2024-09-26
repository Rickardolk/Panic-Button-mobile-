package com.example.panicbutton.screen

import android.util.Log
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panicbutton.R
import com.example.panicbutton.viewmodel.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.panicbutton.component.ConfirmationButton
import com.example.panicbutton.viewmodel.PanicButton
import com.example.panicbutton.viewmodel.PanicButtonData

@Composable
fun DetailLogScreen(
    modifier: Modifier = Modifier,
    viewModel: ViewModel = viewModel(),
    vModel: PanicButton = viewModel(),
    nomorRumah: String
) {
    val detailLogData by viewModel.panicButtonData.observeAsState(emptyList())

    LaunchedEffect(nomorRumah) {
        viewModel.detailLog(nomorRumah)
        Log.d("DetailLog", "Data: $detailLogData")
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
                text = "Detail Rekap",
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
                        text = nomorRumah,
                        fontSize = 20.sp,
                        color = colorResource(id = R.color.font2),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            Spacer(modifier = Modifier.height(21.dp))
            LazyColumn(
                modifier
                    .padding(bottom = 40.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 26.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(detailLogData) { log ->
                    DetailRekapItem(log = log, vModel = vModel)
                }
            }
        }
    }
}


@Composable
fun DetailRekapItem(
    log: PanicButtonData,
    modifier: Modifier = Modifier,
    vModel: PanicButton = viewModel()
) {
    var btnSelesai by remember {mutableStateOf(vModel.isLogCompleted(log.id))}

    Card(
        modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(bottom = 4.dp),
        colors = if (btnSelesai) CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.background_card)
        ) else CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = log.nomor_rumah,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.font2)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    modifier
                        .wrapContentWidth()
                        .height(22.dp)
                        .background(
                            color = when (log.prioritas) {
                                "Darurat" -> colorResource(id = R.color.darurat)
                                "Penting" -> colorResource(id = R.color.penting)
                                else -> colorResource(id = R.color.biasa)
                            }, RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 4.dp)
                ) {
                    Text(
                        text = log.prioritas,
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
                Box(
                    modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        text = log.waktu,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = colorResource(id = R.color.primary)
                    )
                }
            }
            Text(
                text = log.pesan,
                fontSize = 12.sp,
                color = colorResource(id = R.color.font3),
                style = TextStyle(lineHeight = 20.sp),
                overflow = TextOverflow.Ellipsis
            )
            ConfirmationButton(
                modifier = Modifier.align(Alignment.End),
                onConfirm = {
                    vModel.updateLogStatus(log.id, "selesai")
                    vModel.simpanLogStatus(log.id, true)
                    btnSelesai = true
                },
                enabled = !vModel.isLogCompleted(log.id)
            )
        }
    }
}

@Preview
@Composable
private fun Liat() {
    DetailLogScreen(
        nomorRumah = "1234"
    )
}