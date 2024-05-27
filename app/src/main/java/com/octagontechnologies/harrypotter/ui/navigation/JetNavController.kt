package com.octagontechnologies.harrypotter.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.octagontechnologies.harrypotter.domain.Character


object Screens {

    const val HOME = "Home"

    const val DETAILS = "Details"
    const val DETAILS_CHARACTER_ID=  "character_id"


}


@Composable
fun rememberJetNavController(navController: NavHostController = rememberNavController()): JetNavController =
    remember {
        JetNavController(navController)
    }


class JetNavController(
    val navController: NavHostController,
) {

    private val currentRoute = navController.currentDestination?.route

    /**
     * Returns to the previous screen
     */
    fun upPress() {
        navController.popBackStack()
    }

    fun popToHome() {
        if (Screens.HOME != currentRoute) {
            navController.navigate(Screens.HOME) {
                launchSingleTop = true
                restoreState = false

                popUpTo(navController.graph.startDestinationId) {
                    saveState = false
                }
            }
        }
    }


    fun navigateToDetailsScreen(character: Character) {
        navController.navigate("${Screens.DETAILS}/${character.ID}")
    }

}