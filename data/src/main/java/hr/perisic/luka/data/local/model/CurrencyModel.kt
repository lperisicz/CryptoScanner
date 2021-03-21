package hr.perisic.luka.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "currencies")
data class CurrencyModel(
    @PrimaryKey val id: String,
    val name: String,
    val symbol: String,
    @SerializedName("market_cap_rank") val marketCapRank: Int
)