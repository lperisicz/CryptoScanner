package hr.perisic.luka.currency.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import hr.perisic.luka.base.Constants
import hr.perisic.luka.base.BaseViewModel
import hr.perisic.luka.data.remote.model.Currency
import hr.perisic.luka.data.repo.repos.CurrencyRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

internal class CurrencySearchViewModel(
    private val currencyRepository: CurrencyRepository
) : BaseViewModel() {

    val currencyList = MutableLiveData<PagingData<Currency>>()

    private val keyword = BehaviorSubject.createDefault("")

    fun getCurrencies() {
        compositeDisposable.add(
            keyword
                .subscribeOn(Schedulers.io())
                .toFlowable(BackpressureStrategy.LATEST)
                .debounce(Constants.DEBOUNCE_INTERVAL, TimeUnit.MILLISECONDS)
                .flatMap {
                    currencyRepository.filterCurrencies(
                        keyword = it
                    )
                }
                .cachedIn(viewModelScope)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    currencyList.value = it
                }
        )
    }

    fun search(keyword: String) {
        this.keyword.onNext(keyword)
    }


}