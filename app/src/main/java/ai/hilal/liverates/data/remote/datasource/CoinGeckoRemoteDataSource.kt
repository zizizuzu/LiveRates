package ai.hilal.liverates.data.remote.datasource

import ai.hilal.liverates.data.remote.api.CoinGeckoApi
import ai.hilal.liverates.domain.Response
import ai.hilal.liverates.domain.model.Currency
import ai.hilal.liverates.domain.model.CurrencyRate
import android.util.Log
import java.util.Locale
import javax.inject.Inject

class CoinGeckoRemoteDataSource @Inject constructor(
    private val api: CoinGeckoApi
) : RemoteCurrencyDataSource {

    override suspend fun getAvailableCurrencies(): Response<List<Currency>> = safeApiCall(
        errorMessage = "Failed to fetch available currencies",
        action = {
            api.getCoinMarkets(perPage = 100).map { coin ->
                Currency(
                    code = coin.symbol.uppercase(),
                    name = coin.name,
                    imageUrl = coin.imageUrl
                )
            }
        }
    )

    override suspend fun getRates(
        currencyPairs: List<Pair<String, String>>
    ): Response<List<CurrencyRate>> = safeApiCall(
        errorMessage = "Failed to fetch rates",
        action = {
            val response = api.getSimplePrices(
                ids = currencyPairs.joinToString(",") { it.first },
                vsCurrencies = currencyPairs.joinToString(",") { it.second },
                include24hrChange = true
            )

            currencyPairs.mapNotNull { (crypto, fiat) ->
                response[crypto.lowercase()]?.let { ratesMap ->
                    val fiatCode = fiat.lowercase(Locale.getDefault())
                    CurrencyRate(
                        currencyPair = crypto to fiat,
                        price = ratesMap[fiatCode] ?: 0.0,
                        percentageChange24h = ratesMap["${fiatCode}_24h_change"] ?: 0.0
                    )
                }
            }
        }
    )

    private suspend fun <T> safeApiCall(
        errorMessage: String,
        action: suspend () -> T
    ): Response<T> = try {
        Response.success(action())
    } catch (e: Exception) {
        Log.e(TAG, errorMessage, e)
        Response.error(e)
    }

    companion object {
        private const val TAG = "CoinGeckoRemoteDataSource"
    }
}