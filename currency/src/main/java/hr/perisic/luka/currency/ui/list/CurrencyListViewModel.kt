package hr.perisic.luka.currency.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import hr.perisic.luka.base.BaseViewModel
import hr.perisic.luka.data.remote.model.Currency
import hr.perisic.luka.data.repo.repos.CurrencyRepository

internal class CurrencyListViewModel(
    private val currencyRepository: CurrencyRepository
) : BaseViewModel() {

    val currencyList = MutableLiveData<PagingData<Currency>>()

    fun getCurrencies() {
        compositeDisposable.add(
            currencyRepository
                .filterCurrencies("")
                .cachedIn(viewModelScope)
                .subscribe {
                    currencyList.value = it
                }
        )
    }

}