package hr.perisic.luka.data.local.di

import androidx.room.Room
import hr.perisic.luka.data.local.LocalDatabase
import org.koin.dsl.module

val localModule = module {

    single {
        Room.databaseBuilder(
            get(),
            LocalDatabase::class.java,
            "cryptoScannerDb"
        ).fallbackToDestructiveMigration()
            .build()
    }

    single {
        get<LocalDatabase>().currencyModelDao()
    }

    single {
        get<LocalDatabase>().favoriteCurrencyDao()
    }

}