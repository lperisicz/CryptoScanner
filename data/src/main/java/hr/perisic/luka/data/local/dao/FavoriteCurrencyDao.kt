package hr.perisic.luka.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import hr.perisic.luka.data.local.model.FavoriteCurrency
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface FavoriteCurrencyDao : BaseDao<FavoriteCurrency> {

    @Query("SELECT * FROM favorite_currencies")
    fun getAll(): Single<List<FavoriteCurrency>>

    @Query("SELECT COUNT(*) FROM favorite_currencies WHERE id LIKE :id")
    fun count(id: String): Flowable<Int>

}