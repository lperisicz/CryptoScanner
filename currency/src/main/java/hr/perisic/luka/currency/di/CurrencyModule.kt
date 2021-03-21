package hr.perisic.luka.currency.di

import hr.perisic.luka.currency.ui.details.CurrencyDetailsViewModel
import hr.perisic.luka.currency.ui.favorite.FavoriteListViewModel
import hr.perisic.luka.currency.ui.list.CurrencyListViewModel
import hr.perisic.luka.currency.ui.search.CurrencySearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val currencyModule = module {

    viewModel {
        CurrencyListViewModel(
            currencyRepository = get()
        )
    }

    viewModel {
        CurrencySearchViewModel(
            currencyRepository = get()
        )
    }

    viewModel {
        CurrencyDetailsViewModel(
            currencyRepository = get()
        )
    }

    viewModel {
        FavoriteListViewModel(
            currencyRepository = get()
        )
    }

}