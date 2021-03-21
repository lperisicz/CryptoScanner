package hr.perisic.luka.currency.ui.details

import android.app.AlarmManager
import androidx.lifecycle.MutableLiveData
import hr.perisic.luka.base.Constants
import hr.perisic.luka.base.BaseViewModel
import hr.perisic.luka.base.TimeFormatter
import hr.perisic.luka.data.remote.model.Currency
import hr.perisic.luka.data.remote.model.Sparkline
import hr.perisic.luka.data.repo.repos.CurrencyRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

internal class CurrencyDetailsViewModel(
    private val currencyRepository: CurrencyRepository
) : BaseViewModel() {

    private val currency = BehaviorSubject.create<String>()
    val selectedInterval = BehaviorSubject.createDefault(
        TimeFormatter.getTimePair(
            interval = AlarmManager.INTERVAL_DAY * 30
        )
    )
    val isCurrencyFavorite = MutableLiveData<Boolean>()

    val sparkline = MutableLiveData<Sparkline>()
    val error = MutableLiveData<String>()

    fun checkIfCurrencyFavorite(currency: Currency) {
        this.currency.onNext(currency.id)
        compositeDisposable.add(
            this.currency
                .subscribeOn(Schedulers.io())
                .toFlowable(BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .flatMap { currencyRepository.isCurrencyFavorite(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    isCurrencyFavorite.value = it
                }
        )
    }

    fun getSparklineData(currency: Currency) {
        compositeDisposable.add(
            selectedInterval
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .throttleLast(Constants.DEBOUNCE_INTERVAL, TimeUnit.MILLISECONDS)
                .flatMapSingle {
                    currencyRepository.getSparklineData(
                        start = it.first,
                        end = it.second,
                        currency = currency.id
                    )
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    sparkline.value = it
                    error.value = null
                }, {
                    error.value = it.localizedMessage
                })
        )
    }

    fun addToFavorites(currency: Currency) {
        compositeDisposable.add(
            currencyRepository
                .addToFavorites(currency)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    fun removeFromFavorites(currency: Currency) {
        compositeDisposable.add(
            currencyRepository
                .removeFromFavorites(currency)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

}