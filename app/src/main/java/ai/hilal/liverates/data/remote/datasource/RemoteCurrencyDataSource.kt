package ai.hilal.liverates.data.remote.datasource

import ai.hilal.liverates.domain.Response
import ai.hilal.liverates.domain.model.Currency
import ai.hilal.liverates.domain.model.CurrencyRate

interface RemoteCurrencyDataSource {
    suspend fun getAvailableCurrencies(): Response<List<Currency>>
    suspend fun getRates(currencyPairs: List<Pair<String, String>>): Response<List<CurrencyRate>>
}