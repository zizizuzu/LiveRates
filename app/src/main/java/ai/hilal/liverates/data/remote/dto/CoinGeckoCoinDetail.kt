package ai.hilal.liverates.data.remote.dto

data class CoinGeckoCoinDetail(
    val id: String,
    val symbol: String,
    val name: String,
    val market_data: CoinGeckoMarketData
)