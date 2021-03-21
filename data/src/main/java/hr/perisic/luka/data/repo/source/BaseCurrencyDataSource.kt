package hr.perisic.luka.data.repo.source

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import hr.perisic.luka.base.Constants
import hr.perisic.luka.data.remote.model.Currency

abstract class BaseCurrencyDataSource : RxPagingSource<Int, Currency>() {

    override fun getRefreshKey(state: PagingState<Int, Currency>): Int {
        return 1
    }

    protected fun toLoadResult(
        data: List<Currency>,
        position: Int,
        loadSize: Int
    ): LoadResult<Int, Currency> {
        return LoadResult.Page(
            data = data,
            prevKey = if (position == 1) null else position - 1,
            nextKey = if (data.size < loadSize) null else position + (data.size / Constants.NETWORK_PAGE_SIZE)
        )
    }

}