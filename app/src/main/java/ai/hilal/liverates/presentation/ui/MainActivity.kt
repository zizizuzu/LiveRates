package ai.hilal.liverates.presentation.ui

import ai.hilal.liverates.R
import ai.hilal.liverates.data.local.datastore.SettingsDataStore
import ai.hilal.liverates.presentation.navigation.AppNavigation
import ai.hilal.liverates.presentation.navigation.Destinations
import ai.hilal.liverates.presentation.ui.theme.LiveRatesTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var dataStore: SettingsDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainContent()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainContent() {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRote = navBackStackEntry?.destination?.route
        val isDarkTheme by dataStore.isDarkTheme.collectAsState(initial = false)

        LiveRatesTheme(darkTheme = isDarkTheme) {
            Scaffold(
                topBar = {
                    when (currentRote) {
                        Destinations.Home.rote -> TopAppBar(title = { Text(stringResource(R.string.app_name)) })
                        Destinations.AddAsset.rote -> TopAppBar(
                            title = { Text(stringResource(R.string.screen_title_add_new_currency)) },
                            navigationIcon = {
                                IconButton(onClick = { navController.popBackStack() }) {
                                    Icon(
                                        Icons.Default.ArrowBack,
                                        contentDescription = stringResource(R.string.content_description_button_back)
                                    )
                                }
                            }
                        )
                    }
                },
                modifier = Modifier.systemBarsPadding(),
                floatingActionButton = {
                    if (currentRote in listOf(Destinations.Home.rote)) {
                        FloatingActionButton(
                            onClick = { navController.navigate(Destinations.AddAsset.rote) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = stringResource(R.string.content_description_button_add_currency)
                            )
                        }
                    }
                },
                bottomBar = {
                    if (currentRote in listOf(Destinations.Home.rote, Destinations.Settings.rote)) {
                        BottomNavigationBar(
                            currentRote = currentRote,
                            navigateToHomeScreen = { navController.navigate(Destinations.Home.rote) },
                            navigateToSettingsScreen = { navController.navigate(Destinations.Settings.rote) }
                        )
                    }
                }
            ) { paddingValues ->
                AppNavigation(
                    navHostController = navController,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}
