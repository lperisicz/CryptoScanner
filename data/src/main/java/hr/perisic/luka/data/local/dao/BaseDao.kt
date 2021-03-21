package hr.perisic.luka.data.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import io.reactivex.Completable

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dataList: List<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: T): Completable

    @Delete
    fun delete(data: T): Completable

}