package com.example.panicbutton.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panicbutton.R

@Composable
fun ConfirmationButton(
    modifier: Modifier = Modifier,
    onConfirm: () -> Unit,
    enabled: Boolean
) {
    Box(
        modifier
            .height(30.dp)
            .width(108.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        if (enabled) {
            Column(
                modifier
                    .width(80.dp)
                    .height(30.dp)
                    .background(colorResource(id = R.color.primary), RoundedCornerShape(16.dp))
                    .clickable {
                        onConfirm()
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Proses",
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        } else {
            Row(
                modifier
                    .height(30.dp)
                    .width(108.dp)
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .padding(start = 20.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_done),
                    contentDescription = "ic_done"
                )
                Text(
                    text = "Selesai",
                    fontSize = 12.sp,
                    color = Color.Black
                )
            }
        }
    }
}


