package hr.perisic.luka.currency.ui.details

import android.app.AlarmManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import hr.perisic.luka.base.BaseFragment
import hr.perisic.luka.base.TimeFormatter.getTimePair
import hr.perisic.luka.currency.R
import hr.perisic.luka.currency.databinding.FragmentCurrencyDetailsBinding
import hr.perisic.luka.currency.helpers.*
import hr.perisic.luka.data.remote.model.Currency
import org.koin.android.viewmodel.ext.android.viewModel

internal class CurrencyDetailsFragment : BaseFragment<FragmentCurrencyDetailsBinding>() {

    private val args by navArgs<CurrencyDetailsFragmentArgs>()
    private val viewModel by viewModel<CurrencyDetailsViewModel>()
    private val sparklineAdapter = SparklineAdapter()

    override val layoutId: Int = R.layout.fragment_currency_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.checkIfCurrencyFavorite(args.currency)
        viewModel.getSparklineData(args.currency)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        observeData()
    }

    private fun setupUi() {
        setupFavorite()
        setupSparkline()
        displayTitle(args.currency)
        displayPrices(args.currency)
    }

    private fun displayTitle(currency: Currency) {
        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = getString(R.string.name_symbol_format, currency.name, currency.symbol)
        }
    }

    private fun setupFavorite() {
        binding.buttonAddToFavorite.setOnClickListener {
            if (!binding.buttonAddToFavorite.isChecked) {
                viewModel.removeFromFavorites(args.currency)
            } else {
                viewModel.addToFavorites(args.currency)
            }
        }
    }

    private fun setupSparkline() {
        binding.sparkViewPriceHistory.adapter = sparklineAdapter
        binding.toggleGroupIntervals.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                viewModel.selectedInterval.onNext(
                    when (checkedId) {
                        R.id.buttonOneDay -> {
                            getTimePair(
                                interval = AlarmManager.INTERVAL_DAY
                            )
                        }
                        R.id.buttonTwoDays -> {
                            getTimePair(
                                interval = AlarmManager.INTERVAL_DAY * 2
                            )
                        }
                        R.id.buttonOneMonth -> {
                            getTimePair(
                                interval = AlarmManager.INTERVAL_DAY * 30
                            )
                        }
                        R.id.buttonOneYear -> {
                            getTimePair(
                                interval = AlarmManager.INTERVAL_DAY * 365
                            )
                        }
                        else -> getTimePair(
                            interval = AlarmManager.INTERVAL_DAY * 2
                        )
                    }
                )
            }
        }
        binding.sparkViewPriceHistory.setScrubListener {
            it?.let {
                binding.textViewScrub.visibility = View.VISIBLE
                binding.textViewScrub.text = it.toString()
            } ?: let {
                binding.textViewScrub.visibility = View.GONE
            }
        }
    }

    private fun displayPrices(currency: Currency) {
        binding.currency = currency
        displayRank(binding.textViewRank, currency.rank)
        displayLogo(binding.imageViewLogo, currency.logoUrl, currency.symbol)
        displayPrice(binding.textViewPrice, currency.price)
        displayPriceChange(binding.textViewPriceChange, currency.priceChange)
        displayPercentChange(binding.textViewPricePercent, currency.priceChange)
        displayPrice(binding.textViewAllTimeHigh, currency.high)
        displayDate(binding.textViewAllTimeHighDate, currency.highTimestamp)
    }

    private fun observeData() {
        viewModel.isCurrencyFavorite.observe(viewLifecycleOwner, {
            binding.buttonAddToFavorite.isChecked = it
        })
        viewModel.sparkline.observe(viewLifecycleOwner, {
            it?.let { it1 -> sparklineAdapter.submitData(it1) }
        })
        viewModel.error.observe(viewLifecycleOwner, {
            it?.let {
                binding.textViewSparklineError.visibility = View.VISIBLE
                binding.textViewSparklineError.text = it
            } ?: let {
                binding.textViewSparklineError.visibility = View.GONE
            }
        })
    }

}