package com.example.panicbutton.screen


import android.content.Context
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.panicbutton.R
import com.example.panicbutton.component.LogoutIcon
import com.example.panicbutton.component.ToggleSwitch

@Composable
fun UserDashboard(
    modifier: Modifier = Modifier,
    context: Context,
    snackbarHostState: SnackbarHostState,
    navController: NavController
) {
    val sharedPref = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
    val nomorRumah = sharedPref.getString("nomorRumah", "Nomor rumah tidak ditemukan")

    Box(
        modifier
            .background(color = colorResource(id = R.color.background))
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier
                .background(color = colorResource(id = R.color.primary))
                .height(180.dp)
                .fillMaxWidth()
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Panic Button",
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
        Column(
            modifier
                .padding(start = 24.dp, end = 24.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier
                    .padding(top = 140.dp)
                    .height(114.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    contentColor = Color.White,
                    containerColor = Color.White
                )
            ) {
                Row(
                    modifier
                        .padding(start = 22.dp, end = 22.dp)
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Selamat Datang",
                            fontSize = 14.sp,
                            color = colorResource(id = R.color.font2)
                        )
                        Text(
                            text = "Rickardo",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = colorResource(id = R.color.font)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "Nomor rumah anda:",
                                fontSize = 14.sp,
                                color = colorResource(id = R.color.font2)
                            )
                            Text(
                                text = "$nomorRumah",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(id = R.color.font2)
                            )
                        }
                    }
                    LogoutIcon(navController = navController)
                }
            }
            Column(
                modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .background(
                        color = colorResource(id = R.color.primary),
                        RoundedCornerShape(16.dp)
                    )
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Row(
                    modifier
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_warning),
                        contentDescription = "ic_warning",
                        tint = colorResource(id = R.color.background)
                    )
                    Text(
                        text = "Peringatan",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
                Text(
                    text = "Gunakan tombol hanya untuk keadaan darurat atau gangguan lainnya",
                    color = Color.White)
            }
            ToggleSwitch(snackbarHostState = snackbarHostState)
            Column(
                modifier
                    .fillMaxSize()
                    .background(
                        color = Color.White,
                        RoundedCornerShape(16.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Berisi Informasi",
                    color = colorResource(id = R.color.font2)
                )
            }
        }
    }
}




