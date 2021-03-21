package hr.perisic.luka.data.repo.source

import hr.perisic.luka.base.Constants
import hr.perisic.luka.data.local.dao.FavoriteCurrencyDao
import hr.perisic.luka.data.local.model.FavoriteCurrency
import hr.perisic.luka.data.remote.model.Currency
import hr.perisic.luka.data.remote.service.CurrencyService
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

internal class FavoriteCurrencyDataSource(
    private val currencyService: CurrencyService,
    private val favoriteCurrencyDao: FavoriteCurrencyDao
) : BaseCurrencyDataSource() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Currency>> {
        val position = params.key ?: 1
        return favoriteCurrencyDao
            .getAll()
            .map { favoriteListToQueryParameter(position, params.loadSize, it) }
            .flatMap { filterIfQueryNotEmpty(it, position, params.loadSize) }
            .subscribeOn(Schedulers.io())
            .map { toLoadResult(it, position, params.loadSize) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun favoriteListToQueryParameter(
        position: Int,
        loadSize: Int,
        data: List<FavoriteCurrency>
    ): String {
        val fromIndex = (position - 1) * Constants.NETWORK_PAGE_SIZE
        val lastItemIndex = fromIndex + loadSize
        val toIndex = if (data.size > lastItemIndex) {
            lastItemIndex
        } else data.size
        return data.subList(
            fromIndex = fromIndex,
            toIndex = toIndex
        ).joinToString(",") { item -> item.id }
    }

    private fun filterIfQueryNotEmpty(
        query: String,
        page: Int,
        loadSize: Int
    ): Single<List<Currency>> {
        return if (query.isNotEmpty()) {
            currencyService.filterCurrencies(
                page = page,
                limit = loadSize,
                ids = query
            )
        } else {
            Single.just(listOf())
        }
    }

}