package ai.hilal.liverates.data.remote.dto

data class CoinGeckoMarketData(
    val current_price: Map<String, Double>,
    val price_change_percentage_24h: Double?
)