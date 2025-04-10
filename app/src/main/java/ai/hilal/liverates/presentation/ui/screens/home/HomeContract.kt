package ai.hilal.liverates.presentation.ui.screens.home

data class HomeState(
    val currencies: List<CurrencyUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class HomeEvent {
    data object LoadData : HomeEvent()
    data class RemoveCurrency(val item: CurrencyUiModel) : HomeEvent()
    data object RefreshRates : HomeEvent()
}

data class CurrencyUiModel(
    val from: String,
    val to: String,
    val price: String,
    val rate: String,
    val isUp: Boolean
)