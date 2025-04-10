package ai.hilal.liverates.data.local.dao

import ai.hilal.liverates.data.local.entity.CurrencyEntity
import ai.hilal.liverates.data.local.entity.CurrencyRateEntity
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(currencies: List<CurrencyEntity>)

    @Query("SELECT * FROM currencies")
    suspend fun getAllCurrencies(): List<CurrencyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRates(rates: List<CurrencyRateEntity>)

    @Query("SELECT * FROM currency_rates")
    fun getAllRates(): List<CurrencyRateEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRate(rate: CurrencyRateEntity)

    @Delete
    suspend fun deleteRate(rate: CurrencyRateEntity)

    @Query("DELETE FROM currency_rates WHERE pair_key = :pairKey")
    suspend fun deleteRateByPair(pairKey: String)

    @Query("DELETE FROM currency_rates")
    suspend fun clearRates()
}