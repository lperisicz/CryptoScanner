package hr.perisic.luka.base

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object TimeFormatter {

    @SuppressLint("ConstantLocale")
    private val serverTimeFormatter =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())

    @SuppressLint("ConstantLocale")
    private val displayTimeFormatter =
        SimpleDateFormat("dd.MM.yyyy.", Locale.getDefault())

    fun formatToFloat(time: String): Float {
        val date = serverTimeFormatter.parse(time)
        return date?.time?.toFloat() ?: 0f
    }

    fun formatTimeToString(time: String): String {
        val date = serverTimeFormatter.parse(time)
        return date?.let {
            displayTimeFormatter.format(it)
        } ?: "-"
    }

    fun getTimePair(interval: Long): Pair<String, String> {
        val now = System.currentTimeMillis()
        val then = now - interval
        return Pair(
            serverTimeFormatter.format(Date(then)),
            serverTimeFormatter.format(Date(now))
        )
    }

}