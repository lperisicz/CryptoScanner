package hr.perisic.luka.currency.ui.list

import android.os.Bundle
import android.view.View
import androidx.paging.LoadState
import hr.perisic.luka.base.BaseFragment
import hr.perisic.luka.base.LoadingStateAdapter
import hr.perisic.luka.currency.R
import hr.perisic.luka.currency.databinding.FragmentCurrencyListBinding
import org.koin.android.viewmodel.ext.android.viewModel

internal class CurrencyListFragment : BaseFragment<FragmentCurrencyListBinding>() {

    private val currenciesAdapter = CurrencyListPagedAdapter()
    private val loadingStateAdapter = LoadingStateAdapter {
        currenciesAdapter.retry()
    }
    private val viewModel by viewModel<CurrencyListViewModel>()

    override val layoutId: Int = R.layout.fragment_currency_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCurrencies()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        observeData()
    }

    private fun setupUi() {
        binding.swipeRefreshLayout.setOnRefreshListener { currenciesAdapter.refresh() }
        setupAdapter()
    }

    private fun setupAdapter() {
        binding
            .recyclerViewCurrencies
            .adapter = currenciesAdapter.withLoadStateFooter(loadingStateAdapter)
        currenciesAdapter.addLoadStateListener {
            binding.swipeRefreshLayout.isRefreshing = it.refresh is LoadState.Loading
        }
    }

    private fun observeData() {
        viewModel.currencyList.observe(viewLifecycleOwner, {
            currenciesAdapter.submitData(lifecycle, it)
        })
    }

}