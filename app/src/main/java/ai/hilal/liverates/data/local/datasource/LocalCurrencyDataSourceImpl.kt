package ai.hilal.liverates.data.local.datasource

import ai.hilal.liverates.data.local.dao.CurrencyDao
import ai.hilal.liverates.data.local.entity.CurrencyEntity
import ai.hilal.liverates.data.local.entity.CurrencyRateEntity
import ai.hilal.liverates.domain.model.Currency
import ai.hilal.liverates.domain.model.CurrencyRate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class LocalCurrencyDataSourceImpl @Inject constructor(
    private val currencyDao: CurrencyDao,
) : LocalCurrencyDataSource {
    private val _selectedRates: MutableStateFlow<List<CurrencyRate>> = MutableStateFlow(emptyList())
    private val availableCurrenciesCached: MutableList<Currency> = mutableListOf()

    override val selectedRates: StateFlow<List<CurrencyRate>> = _selectedRates

    override suspend fun saveAvailableCurrencies(currencies: List<Currency>) {
        availableCurrenciesCached.clear()
        availableCurrenciesCached.addAll(currencies)
        currencyDao.insertCurrencies(currencies.map { it.toEntity() })
    }

    override suspend fun getAvailableCurrencies(): List<Currency> {
        val result = currencyDao.getAllCurrencies().map { it.toDomain() }
        availableCurrenciesCached.clear()
        availableCurrenciesCached.addAll(result)
        return result
    }

    override suspend fun initRates() {
        val restoredRates = currencyDao.getAllRates().toDomainModel()
        _selectedRates.emit(restoredRates)
    }

    override suspend fun saveRates(rates: List<CurrencyRate>) {
        currencyDao.clearRates()
        currencyDao.insertRates(rates.map { it.toEntity() })
        _selectedRates.update {
            rates.toList()
        }
    }

    override suspend fun addRate(rate: CurrencyRate) {
        currencyDao.insertRate(rate.toEntity())
        _selectedRates.update {
            it.toMutableList().also { list -> list.add(rate) }
        }
    }

    override suspend fun removeRate(rate: CurrencyRate) {
        currencyDao.deleteRateByPair("${rate.currencyPair.first}_${rate.currencyPair.second}")
        _selectedRates.update {
            if (rate in it) {
                it.toMutableList().also { list -> list.remove(rate) }
            } else {
                it
            }
        }
    }

    // Mapper extensions
    private fun Currency.toEntity() = CurrencyEntity(
        code = code,
        name = name,
        imageUrl = imageUrl
    )

    private fun CurrencyEntity.toDomain() = Currency(
        code = code,
        name = name,
        imageUrl = imageUrl ?: ""
    )

    private fun CurrencyRate.toEntity() = CurrencyRateEntity(
        pairKey = "${currencyPair.first}_${currencyPair.second}",
        fromCurrency = currencyPair.first,
        toCurrency = currencyPair.second,
        price = price,
        percentageChange24h = percentageChange24h
    )

    private fun List<CurrencyRateEntity>.toDomainModel() = map {
        CurrencyRate(
            currencyPair = it.fromCurrency to it.toCurrency,
            price = it.price,
            percentageChange24h = it.percentageChange24h
        )
    }
}