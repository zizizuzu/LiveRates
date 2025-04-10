package ai.hilal.liverates.data.repository

import ai.hilal.liverates.data.local.datasource.LocalCurrencyDataSource
import ai.hilal.liverates.data.remote.datasource.RemoteCurrencyDataSource
import ai.hilal.liverates.domain.Response
import ai.hilal.liverates.domain.model.Currency
import ai.hilal.liverates.domain.model.CurrencyRate
import ai.hilal.liverates.domain.onSuccess
import ai.hilal.liverates.domain.repository.CurrencyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteCurrencyDataSource,
    private val localDataSource: LocalCurrencyDataSource,
) : CurrencyRepository {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override val ratesFlow: StateFlow<List<CurrencyRate>> = localDataSource.selectedRates

    init {
        initialize()
    }

    private fun initialize() {
        scope.launch {
            val availableCurrencies = localDataSource.getAvailableCurrencies()
            if (availableCurrencies.isEmpty()) {
                remoteDataSource.getAvailableCurrencies().onSuccess {
                    localDataSource.saveAvailableCurrencies(it)
                }
            }
            refreshRates()
        }
    }

    override suspend fun getAllAvailableCurrencies(): Response<List<Currency>> = withContext(Dispatchers.IO) {
        val currencies = localDataSource.getAvailableCurrencies()
        if (currencies.isEmpty()) {
            remoteDataSource.getAvailableCurrencies()
        } else {
            Response.success(currencies)
        }
    }

    override fun refreshRates() {
        scope.launch {
            if (ratesFlow.value.isNotEmpty()) {
                remoteDataSource.getRates(ratesFlow.value.map { it.currencyPair })
                    .onSuccess { response ->
                        localDataSource.saveRates(response)
                    }
            } else {
                localDataSource.initRates()
            }
        }
    }

    override fun addRate(currencyPair: Pair<String, String>) {
        scope.launch {
            localDataSource.addRate(
                CurrencyRate(
                    currencyPair = currencyPair,
                    price = 0.0,
                    percentageChange24h = 0.0
                )
            )
            refreshRates()
        }
    }

    override fun removeRate(currencyPair: Pair<String, String>) {
        scope.launch {
            ratesFlow.value.firstOrNull { it.currencyPair == currencyPair }?.let {
                localDataSource.removeRate(it)
            }
            refreshRates()
        }
    }
}