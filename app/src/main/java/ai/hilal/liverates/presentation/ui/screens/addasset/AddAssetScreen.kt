package ai.hilal.liverates.presentation.ui.screens.addasset

import ai.hilal.liverates.presentation.ui.components.ErrorState
import ai.hilal.liverates.presentation.ui.components.LoadingState
import ai.hilal.liverates.presentation.ui.screens.addasset.components.CurrencyList
import ai.hilal.liverates.presentation.ui.screens.addasset.components.SearchBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AddAssetScreen(
    viewModel: AddAssetViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) { viewModel.handleEvent(AddAssetEvent.LoadAllCurrencies) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SearchBar(
            query = state.searchQuery,
            onQueryChange = { query ->
                viewModel.handleEvent(AddAssetEvent.OnSearchQueryChanged(query))
            }
        )

        when {
            state.isLoading -> LoadingState()
            state.error != null -> ErrorState(state.error!!)
            else -> CurrencyList(
                currencies = state.filteredCurrencies,
                onClick = { currency ->
                    viewModel.handleEvent(AddAssetEvent.OnCurrencySelected(currency))
                }
            )
        }
    }
}