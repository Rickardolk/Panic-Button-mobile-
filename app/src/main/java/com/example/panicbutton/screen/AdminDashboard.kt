package com.example.panicbutton.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panicbutton.R
import com.example.panicbutton.viewmodel.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.panicbutton.component.LatestDataRekap
import com.example.panicbutton.component.LatestMonitorItem
import com.example.panicbutton.component.MonitorItem

@Composable
fun AdminDashboard(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ViewModel = viewModel()
) {

    Column(
        modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.primary)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            textAlign = TextAlign.Center,
            text = "INFORMASI\nDARURAT",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            style = TextStyle(lineHeight = 40.sp)
        )
        Spacer(modifier = Modifier.height(28.dp))

        MonitorItem(
            viewModel = viewModel,
            navController = navController
        )
        Spacer(modifier = Modifier.height(16.dp))

        LatestMonitorItem(
            viewModel = viewModel,
            navController = navController
        )
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier
                .fillMaxSize()
                .height(310.dp)
                .background(color = Color.White)
        ) {
            Row(
                modifier
                    .padding(start = 24.dp, end = 24.dp, top = 12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
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
            LatestDataRekap(
                navController = navController
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
private fun liat() {
    AdminDashboard(navController = rememberNavController())
}
