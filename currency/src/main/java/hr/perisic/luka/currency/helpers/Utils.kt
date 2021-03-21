package hr.perisic.luka.currency.helpers

import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import hr.perisic.luka.base.TimeFormatter.formatTimeToString
import hr.perisic.luka.currency.R
import hr.perisic.luka.data.remote.model.PriceChange
import java.util.*

fun displayRank(textView: TextView, rank: String?) {
    textView.text = textView.resources.getString(R.string.market_cap_rank_format, rank ?: "-")
}

fun displayLogo(imageView: ImageView, url: String, symbol: String) {
    Glide.with(imageView)
        .load(url)
        .error(
            Glide.with(imageView)
                .load("https://cryptoicons.org/api/icon/${symbol.toLowerCase(Locale.getDefault())}/100")
                .error(R.drawable.ic_coin)
        )
        .placeholder(R.drawable.ic_coin)
        .into(imageView)
}

fun displayPrice(textView: TextView, price: String?) {
    val priceFormatted = price?.let { String.format("%.2f", it.toDoubleOrNull()) } ?: "-"
    textView.text = textView.resources.getString(R.string.price_format, priceFormatted)
}

fun displayPriceChange(textView: TextView, priceChange: PriceChange?) {
    textView.text = priceChange?.let {
        textView.resources.getString(
            R.string.price_change_format,
            it.change.toDouble().times(100)
        ) + "$"
    } ?: "?$"
}

fun displayPercentChange(textView: TextView, priceChange: PriceChange?) {
    textView.text = priceChange?.let {
        textView.resources.getString(
            R.string.percent_change_format,
            it.changePercent.toDouble().times(100)
        ) + "%"
    } ?: "?%"
}

fun displayDate(textView: TextView, date: String?) {
    textView.text = date?.let { formatTimeToString(it) } ?: "-"
}