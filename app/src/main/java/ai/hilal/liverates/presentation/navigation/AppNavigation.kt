package ai.hilal.liverates.presentation.navigation

import ai.hilal.liverates.presentation.ui.screens.addasset.AddAssetScreen
import ai.hilal.liverates.presentation.ui.screens.home.HomeScreen
import ai.hilal.liverates.presentation.ui.screens.settings.SettingsScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavigation(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = Destinations.Home.rote,
        modifier = modifier
    ) {
        composable(Destinations.Home.rote) {
            HomeScreen()
        }

        composable(Destinations.AddAsset.rote) {
            AddAssetScreen()
        }
        composable(Destinations.Settings.rote) {
            SettingsScreen()
        }
    }
}