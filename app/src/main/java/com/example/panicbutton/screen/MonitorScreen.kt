package com.example.panicbutton.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.panicbutton.R
import com.example.panicbutton.component.Monitoring

@Composable
fun ScreenMonitor(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column(
        modifier
            .padding(top = 40.dp, start = 24.dp, end = 24.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.merah), RoundedCornerShape(24.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text =" Informasi Darurat",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
        Spacer(modifier.height(16.dp))
        Monitoring(
            viewModel = viewModel(),
            navController = navController
        )
    }
}

