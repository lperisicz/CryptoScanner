package hr.perisic.luka.data.repo.source

import hr.perisic.luka.base.Constants
import hr.perisic.luka.data.local.dao.CurrencyModelDao
import hr.perisic.luka.data.local.model.CurrencyModel
import hr.perisic.luka.data.remote.model.Currency
import hr.perisic.luka.data.remote.service.CurrencyService
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.*

internal class CurrencySearchDataSource(
    private val currencyService: CurrencyService,
    private val currencyModelDao: CurrencyModelDao,
    private val keyword: String
) : BaseCurrencyDataSource() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Currency>> {
        val position = params.key ?: 1
        if (keyword.isNotEmpty()) {
            return currencyModelDao.getCount()
                .flatMap {
                    if (it > 0) {
                        //db is not empty, proceed with search
                        currencyModelDao.search(keyword)
                    } else {
                        //get list of all coins then get search result
                        currencyService
                            .getAllCurrencies()
                            .doOnSuccess { items ->
                                currencyModelDao.insert(items)
                            }.flatMap {
                                currencyModelDao.search(keyword)
                            }
                    }
                }.map {
                    modelListToQueryParameter(position, params.loadSize, it)
                }.flatMap { filterIfQueryNotEmpty(it, position, params.loadSize) }
                .subscribeOn(Schedulers.io())
                .map { toLoadResult(it, position, params.loadSize) }
                .onErrorReturn { LoadResult.Error(it) }
        } else {
            return currencyService.filterCurrencies(
                page = position,
                limit = params.loadSize
            )
                .subscribeOn(Schedulers.io())
                .map { toLoadResult(it, position, params.loadSize) }
                .onErrorReturn { LoadResult.Error(it) }
        }
    }

    private fun modelListToQueryParameter(
        position: Int,
        loadSize: Int,
        data: List<CurrencyModel>
    ): String {
        val fromIndex = (position - 1) * Constants.NETWORK_PAGE_SIZE
        val lastItemIndex = fromIndex + loadSize
        val toIndex = if (data.size > lastItemIndex) {
            lastItemIndex
        } else data.size
        return data.subList(
            fromIndex = fromIndex,
            toIndex = toIndex
        ).joinToString(",") { item -> item.symbol.toUpperCase(Locale.getDefault()) }
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