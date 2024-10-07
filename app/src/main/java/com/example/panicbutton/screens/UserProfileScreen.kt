package com.example.panicbutton.screens

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panicbutton.R
import com.example.panicbutton.viewmodel.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import com.example.panicbutton.component.displayData.KeteranganUser
import kotlinx.coroutines.delay

@Composable
fun UserProfileScreen(
    modifier: Modifier = Modifier,
    viewModel : ViewModel = viewModel(),
    context: Context,
    navController: NavController
) {
    val sharedPref = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
    val userName = sharedPref.getString("namaUser", "nama user tidak ditemukan")
    val nomorRumah = sharedPref.getString("nomorRumah", "norum tidak ada")
    val defaultProfile = R.drawable.ic_empty_profile
    val defaultCover = R.drawable.empty_image
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val coverImageUri = remember { mutableStateOf<Uri?> (null) }
    val ipAdd = context.getString(R.string.ipAdd)
    val profileLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            imageUri.value = it
            viewModel.uploadImageToDatabase(context, uri, "profile") { newImagePath ->
                imageUri.value = Uri.parse("http://$ipAdd/api/$newImagePath")
            }
        }
    }
    val coverLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            coverImageUri.value = it
            viewModel.uploadImageToDatabase(context, uri, "cover") { newImagePath ->
                coverImageUri.value = Uri.parse("http://$ipAdd/api/$newImagePath")
            }
        }

    }

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
                viewModel.getCoverImage(nomorRumah) { imagePath ->
                    if (imagePath != null) {
                        coverImageUri.value = Uri.parse("http://$ipAdd/api/$imagePath")
                        Log.d("UserCover", "Image Path: ${imageUri.value}")
                    } else {
                        coverImageUri.value = null
                    }
                }
            }
            delay(200000)
        }
    }

    Box(
        modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.primary))
    ) {
        Box(
            modifier
                .height(180.dp)
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth(),
                painter = rememberImagePainter(
                    data = coverImageUri.value?.toString() ?: defaultCover,
                    builder = {
                        memoryCacheKey(coverImageUri.value?.toString())
                        diskCachePolicy(CachePolicy.DISABLED)
                    }
                ),
                contentDescription = "cover_image",
                contentScale = ContentScale.Crop
            )
            Row(
                modifier
                    .padding(start = 24.dp, end = 24.dp, top = 40.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape),
                    onClick = {
                        navController.navigate("home")
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = colorResource(id = R.color.ic_back_color),
                        contentColor = colorResource(id = R.color.primary)
                    )
                ) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = null
                    )
                }
                IconButton(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape),
                    onClick = { coverLauncher.launch("image/*") },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = colorResource(id = R.color.ic_back_color),
                        contentColor = colorResource(id = R.color.primary)
                    )
                ) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = null
                    )
                }
            }
        }
        Box(
            modifier
                .wrapContentSize()
                .padding(top = 160.dp)
        ){
            Card(
                modifier
                    .padding(top = 26.dp)
                    .height(54.dp)
                    .fillMaxWidth()
                    .padding(start = 54.dp, end = 24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.elevatedCardElevation(4.dp)
            ) {
                Column(
                    modifier
                        .padding(start = 60.dp, top = 4.dp, end = 6.dp, bottom = 4.dp)
                ) {
                    Text(
                        text = "$userName",
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.font),
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Nomor Rumah Anda:",
                            fontSize = 12.sp
                        )
                        Text(
                            text = "$nomorRumah",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.font2)
                        )
                    }
                }
            }
            Box(
                modifier
                    .wrapContentSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .clip(CircleShape)
                        .size(80.dp)
                        .background(color = Color.White)
                        .border(
                            width = 4.dp,
                            color = colorResource(id = R.color.primary),
                            shape = RoundedCornerShape(100.dp)
                        ),
                    painter = rememberImagePainter(
                        data = imageUri.value?.toString() ?: defaultProfile,
                        builder = {
                            memoryCacheKey(imageUri.value?.toString())
                            diskCachePolicy(CachePolicy.DISABLED)
                        }
                    ),
                    contentDescription = "ic_empty_profile",
                    contentScale = ContentScale.Crop
                )
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { profileLauncher.launch("image/*") },
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = "ic_edit",
                    tint = colorResource(id = R.color.primary)
                )
            }
        }
        Column(
            modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(top = 260.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    modifier = Modifier.padding(start = 24.dp),
                    text = "Keterangan",
                    fontSize = 14.sp,
                    color = Color.White
                )
                KeteranganUser(
                    modifier,
                    context = context
                )
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Pengaturan",
                        fontSize = 14.sp,
                        color = Color.White
                    )
                    TextButton(
                        modifier = Modifier,
                        onClick = { /*TODO*/ },
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.ic_back_color)
                        )
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.ic_language),
                            contentDescription = "ic_language",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Bahasa",
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                    TextButton(
                        modifier = Modifier,
                        onClick = { /*TODO*/ },
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.ic_back_color)
                        )
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.ic_notification),
                            contentDescription = "ic_language",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Notifikasi",
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                    TextButton(
                        modifier = Modifier,
                        onClick = { /*TODO*/ },
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.ic_back_color)
                        )
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.ic_help),
                            contentDescription = "ic_language",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Bantuan",
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
private fun Liat() {
    UserProfileScreen(
        context = LocalContext.current,
        navController = rememberNavController()
    )
}
