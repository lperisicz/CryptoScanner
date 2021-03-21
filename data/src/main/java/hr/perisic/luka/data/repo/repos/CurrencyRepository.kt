package hr.perisic.luka.data.repo.repos

import androidx.paging.PagingData
import hr.perisic.luka.data.remote.model.Currency
import hr.perisic.luka.data.remote.model.Sparkline
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface CurrencyRepository {

    fun filterCurrencies(keyword: String): Flowable<PagingData<Currency>>

    fun getSparklineData(start: String, end: String, currency: String): Single<Sparkline>

    fun addToFavorites(currency: Currency): Completable

    fun removeFromFavorites(currency: Currency): Completable

    fun getFavoriteCurrencies(): Flowable<PagingData<Currency>>

    fun isCurrencyFavorite(id: String): Flowable<Boolean>

}