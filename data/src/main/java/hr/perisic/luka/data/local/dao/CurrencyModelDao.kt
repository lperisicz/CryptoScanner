package hr.perisic.luka.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import hr.perisic.luka.data.local.model.CurrencyModel
import io.reactivex.Single

@Dao
interface CurrencyModelDao : BaseDao<CurrencyModel> {

    @Query("SELECT * FROM currencies WHERE name LIKE '%' || :keyword || '%' ORDER BY marketCapRank COLLATE NOCASE ASC")
    fun search(keyword: String): Single<List<CurrencyModel>>

    @Query("SELECT COUNT(*) FROM currencies")
    fun getCount(): Single<Int>

}