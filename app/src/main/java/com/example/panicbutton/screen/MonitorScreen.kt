package com.example.panicbutton.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.panicbutton.R
import com.example.panicbutton.component.MonitorItem

@Composable
fun MonitorScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column(
        modifier
            .padding(start = 24.dp, end = 24.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.merah), RoundedCornerShape(24.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text =" Informasi Darurat",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
        Spacer(modifier.height(16.dp))
        MonitorItem(
            viewModel = viewModel(),
            navController = navController
        )
    }
}

