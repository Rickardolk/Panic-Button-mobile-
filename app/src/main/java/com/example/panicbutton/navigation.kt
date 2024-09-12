package com.example.panicbutton

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.panicbutton.screen.DataRekapScreen
import com.example.panicbutton.component.OnBoarding
import com.example.panicbutton.screen.DetailLogScreen
import com.example.panicbutton.screen.AdminDashboard
import com.example.panicbutton.screen.UserDashboard
import com.example.panicbutton.screen.LoginScreen
import com.example.panicbutton.screen.RegisterScreen
import com.example.panicbutton.viewmodel.ViewModel

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
    val isOnboardingShown = sharedPreferences.getBoolean("OnBoardingShown", false)
    val viewModel: ViewModel = viewModel()

    LaunchedEffect(Unit) {
        if (!isOnboardingShown) {
            navController.navigate("onboarding")
        } else {
            viewModel.checkUserLogin(context, navController)
        }
    }

    NavHost(
        navController = navController,
        startDestination = if (isOnboardingShown)"login" else "onboarding"
    ) {
        composable("onboarding"){
            OnBoarding(navController = navController)
            LaunchedEffect(Unit) {
                sharedPreferences.edit().putBoolean("OnBoardingShown", true).apply()
            }
        }

        composable("login") {
            LoginScreen(
                navController = navController
            )
        }
        composable("register") {
            RegisterScreen(
                navController = navController
            )
        }
        composable("admin"){
            AdminDashboard(
                navController = navController
            )
        }
        composable("data_rekap") {
            DataRekapScreen(
                modifier = Modifier,
                viewModel,
                navController = navController
            )
        }
        composable("home") {
            UserDashboard(
                snackbarHostState = SnackbarHostState(),
                navController = navController,
                context = context
            )
        }
        composable("detail_log_screen/{nomorRumah}") {backStackEntry ->
            val nomorRumah = backStackEntry.arguments?.getString("nomorRumah")
            DetailLogScreen(
                nomorRumah = nomorRumah ?:"",
                viewModel = viewModel
            )
        }
    }
}