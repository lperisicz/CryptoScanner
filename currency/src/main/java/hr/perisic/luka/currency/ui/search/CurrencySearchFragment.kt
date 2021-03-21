package hr.perisic.luka.currency.ui.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.paging.LoadState
import hr.perisic.luka.base.BaseFragment
import hr.perisic.luka.base.LoadingStateAdapter
import hr.perisic.luka.currency.R
import hr.perisic.luka.currency.databinding.FragmentCurrencySearchBinding
import hr.perisic.luka.currency.ui.list.CurrencyListPagedAdapter
import org.koin.android.viewmodel.ext.android.viewModel

internal class CurrencySearchFragment : BaseFragment<FragmentCurrencySearchBinding>(),
    SearchView.OnQueryTextListener {

    private val viewModel by viewModel<CurrencySearchViewModel>()
    private val currenciesAdapter = CurrencyListPagedAdapter()
    private val loadingStateAdapter = LoadingStateAdapter {
        currenciesAdapter.retry()
    }

    override val layoutId: Int = R.layout.fragment_currency_search

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
        binding.searchViewCurrencyName.setOnQueryTextListener(this)
        binding.searchViewCurrencyName.setIconifiedByDefault(false)
        binding.layoutCurrencyList.swipeRefreshLayout.setOnRefreshListener {
            currenciesAdapter.refresh()
        }
    }

    private fun setupAdapter() {
        binding.layoutCurrencyList.recyclerViewCurrencies.adapter = currenciesAdapter
            .withLoadStateFooter(loadingStateAdapter)
        currenciesAdapter.addLoadStateListener {
            binding.layoutCurrencyList.swipeRefreshLayout.isRefreshing =
                it.refresh is LoadState.Loading
        }
    }

    private fun observeData() {
        viewModel.currencyList.observe(viewLifecycleOwner) {
            currenciesAdapter.submitData(lifecycle, it)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.search(query ?: "")
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.search(newText ?: "")
        return true
    }

}