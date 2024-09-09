package com.example.panicbutton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.panicbutton.ui.theme.PanicButtonTheme
import com.example.panicbutton.viewmodel.RekapData

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PanicButtonTheme {
                MyApp()
            }
        }
    }
}


