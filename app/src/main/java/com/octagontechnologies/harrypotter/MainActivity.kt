package com.octagontechnologies.harrypotter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.octagontechnologies.harrypotter.ui.navigation.Screens
import com.octagontechnologies.harrypotter.ui.navigation.rememberJetNavController
import com.octagontechnologies.harrypotter.ui.screens.details.DetailsScreen
import com.octagontechnologies.harrypotter.ui.screens.home.HomeScreen
import com.octagontechnologies.harrypotter.ui.theme.HarryPotterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        setContent {
            HarryPotterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val coroutineScope = rememberCoroutineScope()
                    val jetNavController = rememberJetNavController()


                    NavHost(
                        navController = jetNavController.navController,
                        startDestination = Screens.HOME
                    ) {
                        composable(Screens.HOME) {
                            HomeScreen(coroutineScope = coroutineScope, jetNavController = jetNavController)
                        }

                        composable("${Screens.DETAILS}/{${Screens.DETAILS_CHARACTER_ID}}") {
                            DetailsScreen(jetNavController)
                        }
                    }
                }
            }
        }
    }
}
