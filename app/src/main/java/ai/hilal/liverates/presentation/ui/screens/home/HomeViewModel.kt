package ai.hilal.liverates.presentation.ui.screens.home

import ai.hilal.liverates.domain.model.CurrencyRate
import ai.hilal.liverates.domain.usecase.GetSelectedRatesUseCase
import ai.hilal.liverates.domain.usecase.RefreshRatesUseCase
import ai.hilal.liverates.domain.usecase.RemoveCurrencyUseCase
import android.icu.text.LocaleDisplayNames.UiListItem
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSelectedRatesUseCase: GetSelectedRatesUseCase,
    private val removeCurrencyUseCase: RemoveCurrencyUseCase,
    private val refreshRatesUseCase: RefreshRatesUseCase
) : ViewModel() {
    private var updateJob: Job? = null

    private val ratesFlow: Flow<List<CurrencyRate>> = getSelectedRatesUseCase()

    private val priceFormat = DecimalFormat("#,###.00", DecimalFormatSymbols(Locale.US))

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    fun handleEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.LoadData -> {
                _state.update { it.copy(isLoading = true) }
                loadData()
            }

            is HomeEvent.RemoveCurrency -> removeCurrency(event.item)
            is HomeEvent.RefreshRates -> startPeriodicUpdate()
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            ratesFlow.collect { rateList ->
                val uiList = rateList.map { rate ->
                    CurrencyUiModel(
                        from = rate.currencyPair.first,
                        to = "USD",
                        price = formatPrice(rate.price),
                        rate = "%.1f".format(rate.percentageChange24h) + "%",
                        isUp = rate.percentageChange24h > 0,
                    )
                }
                _state.update { it.copy(currencies = uiList, isLoading = false) }
            }
        }
    }

    private fun removeCurrency(item: CurrencyUiModel) {
        viewModelScope.launch {
            removeCurrencyUseCase(item.from to item.to)
        }
    }


    private fun formatPrice(price: Double): String = if (price >= 1000) {
        priceFormat.format(price)
    } else {
        String.format(Locale.US, "%.2f", price)
    }

    private fun startPeriodicUpdate() {
        //TODO, not the best way to do a recurring task. I usually prefer WorkManager, but for update drains it seems like a waste of time

        stopPeriodicUpdates()

        updateJob = viewModelScope.launch {

            while (true) {
                refreshRatesUseCase()
                delay(30000)
            }
        }
    }

    private fun stopPeriodicUpdates() {
        updateJob?.cancel()
        updateJob = null
    }

    override fun onCleared() {
        stopPeriodicUpdates()
        super.onCleared()
    }
}