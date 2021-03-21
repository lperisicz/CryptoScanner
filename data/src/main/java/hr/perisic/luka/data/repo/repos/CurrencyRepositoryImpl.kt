package hr.perisic.luka.data.repo.repos

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import hr.perisic.luka.base.Constants
import hr.perisic.luka.data.local.dao.CurrencyModelDao
import hr.perisic.luka.data.local.dao.FavoriteCurrencyDao
import hr.perisic.luka.data.local.model.FavoriteCurrency
import hr.perisic.luka.data.remote.model.Currency
import hr.perisic.luka.data.remote.model.Sparkline
import hr.perisic.luka.data.remote.service.CurrencyService
import hr.perisic.luka.data.repo.source.CurrencySearchDataSource
import hr.perisic.luka.data.repo.source.FavoriteCurrencyDataSource
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

internal class CurrencyRepositoryImpl(
    private val currencyService: CurrencyService,
    private val currencyModelDao: CurrencyModelDao,
    private val favoriteCurrencyDao: FavoriteCurrencyDao
) : CurrencyRepository {

    override fun filterCurrencies(keyword: String): Flowable<PagingData<Currency>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.NETWORK_PAGE_SIZE
            ),
            pagingSourceFactory = {
                CurrencySearchDataSource(
                    currencyService, currencyModelDao, keyword
                )
            }
        ).flowable
    }

    override fun addToFavorites(currency: Currency): Completable {
        return favoriteCurrencyDao.insert(
            FavoriteCurrency(
                id = currency.id,
                name = currency.name
            )
        )
    }

    override fun removeFromFavorites(currency: Currency): Completable {
        return favoriteCurrencyDao.delete(
            FavoriteCurrency(
                id = currency.id,
                name = currency.name
            )
        )
    }

    override fun getFavoriteCurrencies(): Flowable<PagingData<Currency>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.NETWORK_PAGE_SIZE
            ),
            pagingSourceFactory = {
                FavoriteCurrencyDataSource(
                    currencyService, favoriteCurrencyDao
                )
            }
        ).flowable
    }

    override fun isCurrencyFavorite(id: String): Flowable<Boolean> {
        return favoriteCurrencyDao
            .count(id)
            .map { it != 0 }
    }

    override fun getSparklineData(start: String, end: String, currency: String): Single<Sparkline> {
        return currencyService
            .getSparklines(start, end, currency)
            .map { it.firstOrNull() }
    }

}