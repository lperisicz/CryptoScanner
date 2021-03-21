package hr.perisic.luka.currency.ui.details

import android.graphics.RectF
import com.robinhood.spark.SparkAdapter
import hr.perisic.luka.base.TimeFormatter
import hr.perisic.luka.data.remote.model.Sparkline

internal class SparklineAdapter : SparkAdapter() {

    private var data: Sparkline? = null

    fun submitData(data: Sparkline) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getCount(): Int = data?.prices?.size ?: 0

    override fun getItem(index: Int): Any = data?.prices?.getOrNull(index) ?: 0

    override fun getY(index: Int): Float = data?.prices?.getOrNull(index)?.toFloatOrNull() ?: 0f

    override fun getX(index: Int): Float {
        return TimeFormatter.formatToFloat(data?.timestamps?.getOrNull(index) ?: "")
    }

    override fun getDataBounds(): RectF {
        val bounds = super.getDataBounds()
        //bounds.inset(bounds.width(), bounds.height() + bounds.height() / 3)
        return bounds
    }

}