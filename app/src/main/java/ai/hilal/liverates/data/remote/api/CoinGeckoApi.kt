package ai.hilal.liverates.data.remote.api

import ai.hilal.liverates.data.remote.dto.CoinGeckoCoin
import ai.hilal.liverates.data.remote.dto.CoinGeckoCoinDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinGeckoApi {
    @GET("coins/markets")
    suspend fun getCoinMarkets(
        @Query("vs_currency") vsCurrency: String = "usd",
        @Query("ids") ids: String? = null,
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") perPage: Int = 100,
        @Query("page") page: Int = 1,
        @Query("sparkline") sparkline: Boolean = false,
        @Query("price_change_percentage") priceChangePercentage: String = "24h"
    ): List<CoinGeckoCoin>

    @GET("coins/{id}")
    suspend fun getCoinDetails(
        @Path("id") id: String,
        @Query("tickers") tickers: Boolean = false,
        @Query("market_data") marketData: Boolean = true,
        @Query("community_data") communityData: Boolean = false,
        @Query("developer_data") developerData: Boolean = false,
        @Query("sparkline") sparkline: Boolean = false
    ): CoinGeckoCoinDetail

    @GET("simple/price")
    suspend fun getSimplePrices(
        @Query("ids") ids: String,
        @Query("vs_currencies") vsCurrencies: String,
        @Query("include_24hr_change") include24hrChange: Boolean = true
    ): Map<String, Map<String, Double>>
}