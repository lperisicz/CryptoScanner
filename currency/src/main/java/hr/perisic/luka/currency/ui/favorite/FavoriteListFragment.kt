package hr.perisic.luka.currency.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.paging.LoadState
import hr.perisic.luka.base.BaseFragment
import hr.perisic.luka.base.LoadingStateAdapter
import hr.perisic.luka.currency.R
import hr.perisic.luka.currency.databinding.FragmentCurrencyListBinding
import hr.perisic.luka.currency.ui.list.CurrencyListPagedAdapter
import org.koin.android.viewmodel.ext.android.viewModel

internal class FavoriteListFragment : BaseFragment<FragmentCurrencyListBinding>() {

    private val currenciesAdapter = CurrencyListPagedAdapter()
    private val loadingStateAdapter = LoadingStateAdapter {
        currenciesAdapter.refresh()
    }
    private val viewModel by viewModel<FavoriteListViewModel>()

    override val layoutId: Int = R.layout.fragment_currency_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCurrencies()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        setupAdapter()
        observeData()
    }

    private fun setupUi() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            currenciesAdapter.refresh()
        }
    }

    private fun setupAdapter() {
        binding.recyclerViewCurrencies.adapter = currenciesAdapter
            .withLoadStateFooter(loadingStateAdapter)
        currenciesAdapter.addLoadStateListener {
            binding.swipeRefreshLayout.isRefreshing =
                it.refresh is LoadState.Loading
        }
    }

    private fun observeData() {
        viewModel.currencyList.observe(viewLifecycleOwner, {
            currenciesAdapter.submitData(lifecycle, it)
        })
    }

}