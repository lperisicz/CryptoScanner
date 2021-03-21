package hr.perisic.luka.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import hr.perisic.luka.data.local.dao.CurrencyModelDao
import hr.perisic.luka.data.local.dao.FavoriteCurrencyDao
import hr.perisic.luka.data.local.model.CurrencyModel
import hr.perisic.luka.data.local.model.FavoriteCurrency

@Database(
    entities = [
        CurrencyModel::class,
    FavoriteCurrency::class
    ],
    exportSchema = false,
    version = 1
)
internal abstract class LocalDatabase : RoomDatabase() {

    abstract fun currencyModelDao(): CurrencyModelDao

    abstract fun favoriteCurrencyDao(): FavoriteCurrencyDao

}