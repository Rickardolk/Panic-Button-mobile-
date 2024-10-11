package com.example.panicbutton.component.button

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panicbutton.R
import com.example.panicbutton.viewmodel.ViewModel

@Composable
fun TButtonLanguage (
    viewModel: ViewModel,
    onLanguageSelected: (String) -> Unit
) {
    var selectLanguage by remember { mutableStateOf("id") }
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
    val currentLanguage = prefs.getString("App_Lang", "id") ?: "id"
    val option = listOf("id" to "Indonesia", "en" to "English")

    TextButton(
        modifier = Modifier,
        onClick = { expanded = true },
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
            text = stringResource(id = R.string.bahasa),
            color = Color.White,
            fontSize = 12.sp
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier
            .background(Color.White)
    ) {
        option.forEach { (code, label) ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = label,
                        color = colorResource(id = R.color.font2),
                        fontSize = 14.sp
                    )
                },
                onClick = {
                    selectLanguage = code
                    viewModel.setLanguage(context, code)
                    onLanguageSelected(code)
                    currentLanguage
                    expanded = false
                }
            )
        }
    }
}
