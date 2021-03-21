package hr.perisic.luka.currency.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import hr.perisic.luka.base.BaseViewModel
import hr.perisic.luka.data.remote.model.Currency
import hr.perisic.luka.data.repo.repos.CurrencyRepository

internal class FavoriteListViewModel(
    private val currencyRepository: CurrencyRepository
) : BaseViewModel() {

    val currencyList = MutableLiveData<PagingData<Currency>>()

    fun getCurrencies() {
        compositeDisposable.add(
            currencyRepository
                .getFavoriteCurrencies()
                .cachedIn(viewModelScope)
                .subscribe {
                    currencyList.value = it
                }
        )
    }

}