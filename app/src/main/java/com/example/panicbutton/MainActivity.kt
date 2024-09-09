package com.example.panicbutton

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.panicbutton.screen.Dashboard
import com.example.panicbutton.screen.DataRekapScreen
import com.example.panicbutton.screen.HomeScreen
import com.example.panicbutton.screen.LoginScreen
import com.example.panicbutton.ui.theme.PanicButtonTheme
import com.example.panicbutton.screen.MainFunction
import com.example.panicbutton.screen.RegisterScreen
import com.example.panicbutton.viewmodel.ViewModel

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

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    val isOnboardingShown = sharedPreferences.getBoolean("OnBoardingShown", false)
    val viewModel: ViewModel = viewModel()

    LaunchedEffect(Unit) {
        if (!isOnboardingShown) {
            navController.navigate("onboarding")
        } else {
            viewModel.checkUserLogin(context, navController)
        }
    }

    NavHost(navController, startDestination = if (isOnboardingShown)"login" else "onboarding") {
        composable("onboarding"){
            MainFunction(navController = navController)
            LaunchedEffect(Unit) {
                sharedPreferences.edit().putBoolean("OnBoardingShown", true).apply()
            }
        }
        composable("login") { LoginScreen(navController = navController) }
        composable("register") { RegisterScreen(navController = navController) }
        composable("admin"){ Dashboard(navController = navController)}
        composable("data_rekap") { DataRekapScreen(modifier = Modifier, viewModel)}
        composable("home") {
            HomeScreen(
                snackbarHostState = SnackbarHostState(),
                navController = navController,
                modifier = Modifier,
                context = context
            )
        }
    }
}
