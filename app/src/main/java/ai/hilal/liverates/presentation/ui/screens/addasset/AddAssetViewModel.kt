package ai.hilal.liverates.presentation.ui.screens.addasset

import ai.hilal.liverates.domain.model.CurrencyRate
import ai.hilal.liverates.domain.onSuccess
import ai.hilal.liverates.domain.usecase.AddCurrencyUseCase
import ai.hilal.liverates.domain.usecase.GetAllCurrenciesUseCase
import ai.hilal.liverates.domain.usecase.GetSelectedRatesUseCase
import ai.hilal.liverates.domain.usecase.RemoveCurrencyUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAssetViewModel @Inject constructor(
    private val getAllCurrenciesUseCase: GetAllCurrenciesUseCase,
    private val getSelectedCurrenciesUseCase: GetSelectedRatesUseCase,
    private val addCurrencyUseCase: AddCurrencyUseCase,
    private val removeCurrencyUseCase: RemoveCurrencyUseCase
) : ViewModel() {

    private val selectedCurrenciesFlow: StateFlow<List<CurrencyRate>> = getSelectedCurrenciesUseCase()

    private val _state = MutableStateFlow(AddAssetState())
    val state: StateFlow<AddAssetState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            selectedCurrenciesFlow.collect {
                loadAllCurrencies()
            }
        }
    }

    fun handleEvent(event: AddAssetEvent) {
        when (event) {
            is AddAssetEvent.OnSearchQueryChanged -> updateSearchQuery(event.query)
            is AddAssetEvent.OnCurrencySelected -> selectCurrency(event.model)
            is AddAssetEvent.LoadAllCurrencies -> loadAllCurrencies()
        }
    }

    private fun loadAllCurrencies() {
        viewModelScope.launch {
            getAllCurrenciesUseCase().onSuccess { currencyList ->
                val selectedItems = selectedCurrenciesFlow.value
                val uiList = currencyList.map { currency ->
                    CurrencyUiModel(
                        code = currency.code,
                        from = currency.name,
                        to = USD_CODE,
                        url = currency.imageUrl,
                        isSelected = selectedItems.any { it.currencyPair.first == currency.name }
                    )
                }

                _state.update {
                    it.copy(
                        allCurrencies = uiList,
                        filteredCurrencies = filterCurrencies(uiList, it.searchQuery),
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun updateSearchQuery(query: String) {
        _state.update {
            it.copy(
                searchQuery = query,
                filteredCurrencies = filterCurrencies(it.allCurrencies, query)
            )
        }
    }

    private fun filterCurrencies(
        currencies: List<CurrencyUiModel>,
        query: String
    ): List<CurrencyUiModel> {
        return if (query.isBlank()) {
            currencies
        } else {
            currencies.filter {
                it.from.contains(query, ignoreCase = true) ||
                        it.to.contains(query, ignoreCase = true)
            }
        }
    }

    private fun selectCurrency(model: CurrencyUiModel) {
        viewModelScope.launch {
            if (model.isSelected) {
                removeCurrencyUseCase(model.from to USD_CODE)
            } else {
                addCurrencyUseCase(model.from to USD_CODE)
            }
        }
    }

    companion object {
        private const val USD_CODE = "USD"
    }
}