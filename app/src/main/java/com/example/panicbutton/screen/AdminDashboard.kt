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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
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
import com.example.panicbutton.component.LatestMonitorItem
import com.example.panicbutton.component.MonitorItem
import kotlinx.coroutines.delay

@Composable
fun AdminDashboard(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ViewModel = viewModel()
) {
    val monitor by viewModel.panicButtonData.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        while (true) {
            viewModel.monitoring()
            delay(2000)
        }
    }

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
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            style = TextStyle(lineHeight = 40.sp)
        )
        Spacer(modifier = Modifier.height(28.dp))

        monitor.forEach{ log ->
            MonitorItem(
                log = log,
                navController = navController
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.background))
        ) {
            Row(
                modifier
                    .padding(top = 16.dp, start = 24.dp, bottom = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_warning),
                    contentDescription = "ic_warning",
                    tint = colorResource(id = R.color.primary)
                )
                Text(
                    text = "Data Terbaru",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.primary)
                )
            }
            monitor.forEach{ log->
                LatestMonitorItem(
                    log = log,
                    navController = navController,
                    viewModel = viewModel
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    navController.navigate("data_rekap")
                },
                modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.primary)
                )
            ) {
               Text(
                   text = "List Data Rekap",
                   fontSize = 12.sp,
                   fontWeight = FontWeight.Medium,
                   color = Color.White
               )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Liat() {
    AdminDashboard(navController = rememberNavController())
}
