package com.example.panicbutton.screen

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import com.example.panicbutton.R
import com.example.panicbutton.component.LogOutIcon
import com.example.panicbutton.component.ToggleSwitch
import com.example.panicbutton.component.UserHistory
import com.example.panicbutton.viewmodel.ViewModel
import kotlinx.coroutines.delay

@Composable
fun UserDashboard(
    modifier: Modifier = Modifier,
    context: Context,
    snackbarHostState: SnackbarHostState,
    navController: NavController,
    viewModel: ViewModel
) {
    val sharedPref = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
    val nomorRumah = sharedPref.getString("nomorRumah", "Nomor rumah tidak ditemukan")
    val namaUser = sharedPref.getString("namaUser", "Nama user tidak ditemukan")
    val rekapData by viewModel.rekapData.observeAsState(emptyList())
    val defauldProfile = R.drawable.ic_empty_profile
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val ipAdd = context.getString(R.string.ipAdd)

    LaunchedEffect(nomorRumah) {
        while (true) {
            if (nomorRumah != null) {
                viewModel.getProfileImage(nomorRumah) { imagePath ->
                    if (imagePath != null) {
                        imageUri.value = Uri.parse("http://$ipAdd/api/$imagePath")
                        Log.d("UserProfile", "Image Path: ${imageUri.value}")
                    } else {
                        imageUri.value = null
                    }
                }
            }
            delay(2000)
        }
    }

    LaunchedEffect(Unit) {
        while (true){
            viewModel.userHIstory(context)
            delay(2000)
        }
    }

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
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
                    .padding(start = 24.dp, top = 28.dp),
                text = "Selamat Datang",
                fontSize = 14.sp,
                color = Color.White
            )
        }
        Column(
            modifier
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier
                    .padding(top = 140.dp)
                    .height(96.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
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
                        Row(
                            modifier
                                .wrapContentWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(36.dp)
                                    .border(
                                        width = 2.dp,
                                        color = colorResource(id = R.color.font),
                                        shape = RoundedCornerShape(100.dp)
                                    )
                                    .clickable { navController.navigate("user_profile") },
                                painter = rememberImagePainter(
                                    data = imageUri.value?.toString() ?: defauldProfile,
                                    builder = {
                                        memoryCacheKey(imageUri.value?.toString())
                                        diskCachePolicy(CachePolicy.DISABLED)
                                    }
                                ),
                                contentDescription = "profile_image",
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = "$namaUser",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = colorResource(id = R.color.font)
                            )
                        }
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
                    LogOutIcon(navController = navController)
                }
            }
            Column(
                modifier
                    .padding(horizontal = 24.dp)
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
                    )
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Start)
                        .padding(start = 24.dp),
                    text = "Riwayat",
                    color = colorResource(id = R.color.font),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                LazyColumn{
                    items(rekapData) { log ->
                        UserHistory( log = log)
                    }
                }
            }
        }
    }
}

