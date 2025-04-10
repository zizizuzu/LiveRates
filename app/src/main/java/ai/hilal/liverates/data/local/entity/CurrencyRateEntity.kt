package ai.hilal.liverates.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_rates")
data class CurrencyRateEntity(
    @PrimaryKey
    @ColumnInfo(name = "pair_key")
    val pairKey: String,  // "BTC_USD"

    @ColumnInfo(name = "from_currency")
    val fromCurrency: String,

    @ColumnInfo(name = "to_currency")
    val toCurrency: String,

    @ColumnInfo(name = "price")
    val price: Double,

    @ColumnInfo(name = "percentage_change_24h")
    val percentageChange24h: Double,

    @ColumnInfo(name = "last_updated")
    val lastUpdated: Long = System.currentTimeMillis()
)