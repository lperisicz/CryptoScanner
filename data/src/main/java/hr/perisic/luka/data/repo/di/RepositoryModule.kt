package hr.perisic.luka.data.repo.di

import hr.perisic.luka.data.repo.repos.CurrencyRepository
import hr.perisic.luka.data.repo.repos.CurrencyRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    single<CurrencyRepository> {
        CurrencyRepositoryImpl(
            currencyService = get(),
            currencyModelDao = get(),
            favoriteCurrencyDao = get()
        )
    }

}