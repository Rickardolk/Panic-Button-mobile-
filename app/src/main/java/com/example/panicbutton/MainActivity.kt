package com.example.panicbutton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.panicbutton.notiification.createNotificationChannel
import com.example.panicbutton.ui.theme.PanicButtonTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        createNotificationChannel(this)
        setContent {
            PanicButtonTheme {
                MyApp()
            }
        }
    }
}


