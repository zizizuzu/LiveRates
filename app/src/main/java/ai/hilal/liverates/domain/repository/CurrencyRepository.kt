package ai.hilal.liverates.domain.repository

import ai.hilal.liverates.domain.Response
import ai.hilal.liverates.domain.model.Currency
import ai.hilal.liverates.domain.model.CurrencyRate
import kotlinx.coroutines.flow.StateFlow

interface CurrencyRepository {
    val ratesFlow: StateFlow<List<CurrencyRate>>

    suspend fun getAllAvailableCurrencies(): Response<List<Currency>>
    fun refreshRates()
    fun addRate(currencyPair: Pair<String, String>)
    fun removeRate(currencyPair: Pair<String, String>)
}