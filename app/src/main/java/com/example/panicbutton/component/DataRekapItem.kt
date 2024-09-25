package com.example.panicbutton.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.panicbutton.R
import com.example.panicbutton.viewmodel.PanicButtonData

@Composable
fun DataRekapItem(
    log: PanicButtonData,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Card(
        modifier
            .clickable { navController.navigate("detail_log_screen/${log.nomor_rumah}") }
            .wrapContentHeight()
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
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
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = log.nomor_rumah,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.font2)
                )
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
                Text(
                    text = log.waktu,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(id = R.color.primary)
                )
            }
            Text(
                text = log.pesan,
                fontSize = 12.sp,
                color = colorResource(id = R.color.font3),
                maxLines = 2,
                style = TextStyle(lineHeight = 20.sp),
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}