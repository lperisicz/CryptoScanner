package hr.perisic.luka.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Currency(
    val id: String,
    val currency: String,
    val price: String?,
    val symbol: String,
    val name: String,
    val high: String?,
    @SerializedName("high_timestamp") val highTimestamp: String?,
    @SerializedName("market_cap") val marketCap: String?,
    @SerializedName("logo_url") val logoUrl: String,
    val rank: String?,
    @SerializedName("max_supply") val maxSupply: String?,
    @SerializedName("circulating_supply") val availableSupply: String?,
    @SerializedName("1d") val priceChange: PriceChange?
) : Parcelable