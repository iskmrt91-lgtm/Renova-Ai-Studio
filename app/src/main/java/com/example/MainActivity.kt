package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                AppNavGraph()
            }
        }
    }
}

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(onNavigateToLogin = {
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
            })
        }
        composable("login") {
            LoginScreen(onLoginSuccess = {
                navController.navigate("paywall") {
                    popUpTo("login") { inclusive = true }
                }
            })
        }
        composable("paywall") {
            PaywallScreen(onPlanSelected = {
                navController.navigate("form") {
                    popUpTo("paywall") { inclusive = true }
                }
            })
        }
        composable("form") {
            ProjectFormScreen(onPublish = {
                navController.navigate("map")
            })
        }
        composable("map") {
            MapScreen(onNavigateToPassport = {
                navController.navigate("passport")
            })
        }
        composable("passport") {
            PassportScreen(onNavigateToCertificate = {
                navController.navigate("certificate")
            })
        }
        composable("certificate") {
            CertificateScreen()
        }
    }
}
