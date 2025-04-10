package ai.hilal.liverates.domain.model

data class CurrencyRate(
    val currencyPair: Pair<String, String>,
    val price: Double,
    val percentageChange24h: Double
)
