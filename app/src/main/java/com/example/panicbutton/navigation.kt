package com.example.panicbutton

import com.example.panicbutton.screens.DataRekapScreen
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.panicbutton.component.displayData.OnBoarding
import com.example.panicbutton.screens.DetailRekapScreen
import com.example.panicbutton.screens.UserDashboard
import com.example.panicbutton.screens.UserProfileScreen
import com.example.panicbutton.screens.RegisterScreen
import com.example.panicbutton.screens.AdminDashboard
import com.example.panicbutton.screens.LoginScreen
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
                navController = navController,
                context = context,
                viewModel = viewModel
            )
        }
        composable("detail_log_screen/{nomorRumah}") {backStackEntry ->
            val nomorRumah = backStackEntry.arguments?.getString("nomorRumah")
            DetailRekapScreen(
                nomorRumah = nomorRumah ?:"",
                viewModel = viewModel,
                context = context,
                navController = navController
            )
        }
        composable("user_profile") {
            UserProfileScreen(
                context = context,
                navController = navController
            )
        }
    }
}