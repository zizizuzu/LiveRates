package ai.hilal.liverates.presentation.ui.screens.home

import ai.hilal.liverates.presentation.ui.components.ErrorState
import ai.hilal.liverates.presentation.ui.components.LoadingState
import ai.hilal.liverates.presentation.ui.screens.home.components.CurrencyList
import ai.hilal.liverates.presentation.ui.screens.home.components.EmptyState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.handleEvent(HomeEvent.LoadData)
        viewModel.handleEvent(HomeEvent.RefreshRates)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> LoadingState()
            state.error != null -> ErrorState(state.error!!)
            state.currencies.isEmpty() -> EmptyState()
            else -> CurrencyList(state.currencies) { model ->
                viewModel.handleEvent(HomeEvent.RemoveCurrency(model))
            }
        }
    }
}