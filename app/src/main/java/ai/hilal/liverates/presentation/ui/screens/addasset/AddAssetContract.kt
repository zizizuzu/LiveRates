package ai.hilal.liverates.presentation.ui.screens.addasset

data class AddAssetState(
    val allCurrencies: List<CurrencyUiModel> = emptyList(),
    val filteredCurrencies: List<CurrencyUiModel> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class AddAssetEvent {
    data class OnSearchQueryChanged(val query: String) : AddAssetEvent()
    data class OnCurrencySelected(val model: CurrencyUiModel) : AddAssetEvent()
    data object LoadAllCurrencies : AddAssetEvent()
}

data class CurrencyUiModel(
    val from: String,
    val to: String,
    val url: String,
    val isSelected: Boolean
)