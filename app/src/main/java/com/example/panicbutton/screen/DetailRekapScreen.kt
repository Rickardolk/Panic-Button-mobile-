package com.example.panicbutton.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panicbutton.R
import com.example.panicbutton.viewmodel.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.panicbutton.component.ConfirmationButton
import com.example.panicbutton.viewmodel.PanicButton
import com.example.panicbutton.viewmodel.PanicButtonData

@Composable
fun DetailRekapScreen2(
    modifier: Modifier = Modifier,
    viewModel: ViewModel = viewModel(),
    vModel: PanicButton = viewModel(),
    nomorRumah: String,
    context: Context,
    navController : NavController
) {
    val detailLogData by viewModel.panicButtonData.observeAsState(emptyList())
    val rekapData by viewModel.getRekapData.observeAsState(emptyList())
    val ipAdd = context.getString(R.string.ipAdd)
    val baseUrl = "http://$ipAdd/api/"
    val scroll = rememberScrollState()

    LaunchedEffect(nomorRumah) {
        viewModel.getDetailRekap(nomorRumah)
        viewModel.detailLog(nomorRumah)
    }

    Box(
        modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.primary))
    ) {
        Box(
            modifier
                .height(180.dp)
                .fillMaxWidth()
                .background(Color.White)
        ) {
            rekapData.firstOrNull()?.let { rekap ->
                Image(
                    modifier = Modifier
                        .fillMaxWidth(),
                    painter = rememberImagePainter(
                        data = if (!rekap.imageCover.isNullOrEmpty()) baseUrl + rekap.imageCover else R.drawable.empty_image,
                        builder = {
                            error(R.drawable.empty_image)
                        }
                    ),
                    contentDescription = "cover_image",
                    contentScale = ContentScale.Crop
                )
            }
            IconButton(
                modifier = Modifier
                    .padding(top = 40.dp, start = 24.dp)
                    .size(36.dp)
                    .clip(CircleShape),
                onClick = {
                    navController.navigate("admin")
                },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = colorResource(id = R.color.ic_back_color),
                    contentColor = colorResource(id = R.color.primary)
                )
            ) {
                Icon(
                    modifier = Modifier
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null
                )
            }
        }
        Box(
            modifier
                .wrapContentSize()
                .padding(top = 160.dp)
        ){
            Card(
                modifier
                    .padding(top = 26.dp)
                    .height(54.dp)
                    .fillMaxWidth()
                    .padding(start = 54.dp, end = 24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.elevatedCardElevation(4.dp)
            ) {
                Column(
                    modifier
                        .padding(start = 60.dp, top = 4.dp, end = 6.dp, bottom = 4.dp)
                ) {
                    rekapData.firstOrNull()?.let { rekap ->
                        Text(
                            text = rekap.nama?.takeIf { it.isNotEmpty() } ?: "Tidak ada nama",
                            fontSize = 18.sp,
                            color = colorResource(id = R.color.font),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Row(
                        modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Nomor Rumah Anda:",
                            fontSize = 12.sp
                        )
                        Text(
                            text = nomorRumah,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.font2)
                        )
                    }
                }
            }
            Box(
                modifier
                    .wrapContentSize(),
                contentAlignment = Alignment.Center
            ) {
                rekapData.firstOrNull()?.let { rekap ->
                    Image(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .clip(CircleShape)
                            .size(80.dp)
                            .background(color = Color.White)
                            .border(
                                width = 4.dp,
                                color = colorResource(id = R.color.primary),
                                shape = RoundedCornerShape(100.dp)
                            ),
                        painter = rememberImagePainter(
                            data = if (!rekap.imageProfile.isNullOrEmpty()) baseUrl + rekap.imageProfile else R.drawable.ic_empty_profile,
                            builder = {
                                error(R.drawable.ic_empty_profile)
                            }
                        ),
                        contentDescription = "ic_empty_profile",
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Column(
                modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(top = 100.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    modifier = Modifier.padding(start = 24.dp),
                    text = "Keterangan",
                    fontSize = 14.sp,
                    color = Color.White
                )
                rekapData.firstOrNull()?.let { rekap ->
                    Column(
                        modifier
                            .padding(horizontal = 24.dp)
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(16.dp))
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(24.dp)
                                .verticalScroll(scroll),
                            text = rekap.keteranganUser?.takeIf { it.isNotEmpty() } ?: "Tidak ada keterangan",
                            fontSize = 12.sp,
                            color = colorResource(id = R.color.font2),
                            style = TextStyle(lineHeight = 16.sp
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier.padding(start = 24.dp),
                    text = "Detail Rekap",
                    fontSize = 14.sp,
                    color = Color.White
                )
                Column(
                    modifier
                        .fillMaxSize()
                        .background(
                            Color.White,
                            RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        )
                        .padding(top = 24.dp)
                ) {
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
    }
}


@Composable
fun DetailRekapItem(
    log: PanicButtonData,
    modifier: Modifier = Modifier,
    vModel: PanicButton = viewModel()
) {
    var btnSelesai by remember { mutableStateOf(vModel.isLogCompleted(log.id)) }

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