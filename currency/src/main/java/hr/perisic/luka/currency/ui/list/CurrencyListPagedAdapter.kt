package hr.perisic.luka.currency.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import hr.perisic.luka.currency.NavGraphMainDirections
import hr.perisic.luka.currency.databinding.ItemCurrencyBinding
import hr.perisic.luka.data.remote.model.Currency
import hr.perisic.luka.currency.helpers.displayLogo
import hr.perisic.luka.currency.helpers.displayPercentChange
import hr.perisic.luka.currency.helpers.displayPrice
import hr.perisic.luka.currency.helpers.displayRank

internal class CurrencyListPagedAdapter :
    PagingDataAdapter<Currency, CurrencyListPagedAdapter.CurrencyViewHolder>(
        diffCallback = object : DiffUtil.ItemCallback<Currency>() {

            override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
                return oldItem.price == newItem.price &&
                        oldItem.priceChange?.changePercent == newItem.priceChange?.changePercent
            }
        }
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurrencyViewHolder {
        val binding = ItemCurrencyBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    internal class CurrencyViewHolder(
        private val binding: ItemCurrencyBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Currency) {
            binding.apply {
                currency = item
                displayRank(textViewRank, item.rank)
                displayLogo(imageViewLogo, item.logoUrl, item.symbol)
                displayPrice(textViewPrice, item.price)
                displayPercentChange(textViewPercentChange, item.priceChange)
                root.setOnClickListener {
                    root.findNavController().navigate(
                        NavGraphMainDirections.actionGlobalToCurrencyDetails(
                            currency = item
                        )
                    )
                }
            }
        }

    }

}