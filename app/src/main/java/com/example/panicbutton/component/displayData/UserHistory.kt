package com.example.panicbutton.component.displayData

import androidx.compose.foundation.background
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
import com.example.panicbutton.R
import com.example.panicbutton.viewmodel.PanicButtonData

@Composable
fun UserHistory(
    modifier: Modifier = Modifier,
    log: PanicButtonData
) {

    Card(
        modifier
            .padding(horizontal = 24.dp)
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        colors = if (log.status == "selesai") CardDefaults.cardColors(
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
