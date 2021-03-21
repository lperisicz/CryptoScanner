package hr.perisic.luka.data.remote.di

import hr.perisic.luka.base.BuildConfig
import hr.perisic.luka.data.remote.helpers.ApiKeyInterceptor
import hr.perisic.luka.data.remote.service.CurrencyService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val remoteModule = module {

    single {
        OkHttpClient.Builder().apply {
            addInterceptor(ApiKeyInterceptor())
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }.build()
    }

    single {
        Retrofit.Builder().apply {
            baseUrl(BuildConfig.BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            client(get())
        }.build()
    }

    single {
        get<Retrofit>().create(CurrencyService::class.java)
    }

}