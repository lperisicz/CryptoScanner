package hr.perisic.luka.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PriceChange(
    @SerializedName("price_change") val change: String,
    @SerializedName("price_change_pct") val changePercent: String
) : Parcelable