package ai.hilal.liverates.data.local.datasource

import ai.hilal.liverates.domain.model.Currency
import ai.hilal.liverates.domain.model.CurrencyRate
import kotlinx.coroutines.flow.StateFlow

interface LocalCurrencyDataSource {
    val selectedRates: StateFlow<List<CurrencyRate>>

    suspend fun saveAvailableCurrencies(currencies: List<Currency>)
    suspend fun getAvailableCurrencies(): List<Currency>

    suspend fun initRates()
    suspend fun saveRates(rates:List<CurrencyRate>)
    suspend fun addRate(rate: CurrencyRate)
    suspend fun removeRate(rate: CurrencyRate)
}