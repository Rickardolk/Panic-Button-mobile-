package com.example.panicbutton.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
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
import com.example.panicbutton.component.LatestMonitorItem
import com.example.panicbutton.component.MonitorItem
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import com.example.panicbutton.component.LatestDataRekap
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

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
        LatestDataRekap(
            navController = navController
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Liat() {
    AdminDashboard(navController = rememberNavController())
}
