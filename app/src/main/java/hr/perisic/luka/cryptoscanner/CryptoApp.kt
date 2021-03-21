package hr.perisic.luka.cryptoscanner

import android.app.Application
import hr.perisic.luka.data.local.di.localModule
import hr.perisic.luka.data.remote.di.remoteModule
import hr.perisic.luka.data.repo.di.repositoryModule
import hr.perisic.luka.currency.di.currencyModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CryptoApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CryptoApp)
            modules(
                listOf(
                    localModule,
                    remoteModule,
                    repositoryModule,
                    currencyModule
                )
            )
        }
    }

}