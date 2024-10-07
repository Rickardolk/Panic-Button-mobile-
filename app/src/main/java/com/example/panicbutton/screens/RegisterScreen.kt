package com.example.panicbutton.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.panicbutton.R
import com.example.panicbutton.component.button.OutlinedTextFieldPass
import com.example.panicbutton.viewmodel.ViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ViewModel = viewModel()
) {
    var inputNama by remember { mutableStateOf("") }
    val (nomorRumah, setNomorRumah) = remember { mutableStateOf("") }
    val (sandi, setSandi) = remember { mutableStateOf("") }
    val context = LocalContext.current

    Box(
        modifier
            .background(color = colorResource(id = R.color.primary))
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier
                .height(600.dp)
                .background(
                    color = Color.White,
                    RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp),
        ){
            Column(
                modifier
                    .padding(top = 40.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Daftar",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.font)
                )
                Spacer(modifier = Modifier.height(44.dp))
                OutlinedTextField(
                    value = inputNama,
                    onValueChange = { inputNama = it },
                    label = { Text(text = "Nama")},
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_user),
                            contentDescription = "ic_nama",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = colorResource(id = R.color.font),
                        focusedLabelColor = colorResource(id = R.color.font),
                        focusedLeadingIconColor = colorResource(id = R.color.font),
                        unfocusedLeadingIconColor = colorResource(id = R.color.default_color),
                        cursorColor = colorResource(id = R.color.font)
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = nomorRumah,
                    onValueChange = {setNomorRumah(it)},
                    label = { Text(text = "Nomor Rumah") },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_home),
                            contentDescription = "ic_home",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = colorResource(id = R.color.font),
                        focusedLabelColor = colorResource(id = R.color.font),
                        focusedLeadingIconColor = colorResource(id = R.color.font),
                        unfocusedLeadingIconColor = colorResource(id = R.color.default_color),
                        cursorColor = colorResource(id = R.color.font)
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextFieldPass(
                    sandi, setSandi
                )

                Spacer(modifier = Modifier.height(22.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    onClick = {
                        viewModel.register(inputNama, nomorRumah, sandi, context, navController)
                    },
                    shape = RoundedCornerShape(26.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.font),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Daftar",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(36.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Sudah memiliki akun?"
                    )
                    Text(
                        modifier = Modifier
                            .clickable { navController.popBackStack() },
                        text = "Login",
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.font)
                    )
                }
            }
        }
    }
}
