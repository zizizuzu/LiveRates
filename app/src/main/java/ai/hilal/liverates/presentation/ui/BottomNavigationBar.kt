package ai.hilal.liverates.presentation.ui

import ai.hilal.liverates.R
import ai.hilal.liverates.presentation.navigation.Destinations
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun BottomNavigationBar(
    currentRote: String?,
    navigateToHomeScreen: () -> Unit,
    navigateToSettingsScreen: () -> Unit
) {
    val homeLabel = stringResource(R.string.bottom_bar_navigation_home)
    val settingsLabel = stringResource(R.string.bottom_bar_navigation_settings)
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = homeLabel) },
            label = { Text(homeLabel) },
            selected = currentRote == Destinations.Home.rote,
            onClick = navigateToHomeScreen::invoke
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = settingsLabel) },
            label = { Text(settingsLabel) },
            selected = currentRote == Destinations.Settings.rote,
            onClick = navigateToSettingsScreen::invoke
        )
    }
}