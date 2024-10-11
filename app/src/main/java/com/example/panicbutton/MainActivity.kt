package com.example.panicbutton

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.panicbutton.notiification.createNotificationChannel
import com.example.panicbutton.ui.theme.PanicButtonTheme
import com.example.panicbutton.viewmodel.ViewModel
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.language.bm.Languages

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


