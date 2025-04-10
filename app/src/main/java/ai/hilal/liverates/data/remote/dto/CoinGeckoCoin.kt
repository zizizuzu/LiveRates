package ai.hilal.liverates.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CoinGeckoCoin(
    @SerializedName("id") val id: String,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val imageUrl:String
)