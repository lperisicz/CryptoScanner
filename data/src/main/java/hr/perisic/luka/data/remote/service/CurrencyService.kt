package hr.perisic.luka.data.remote.service

import hr.perisic.luka.data.remote.model.Currency
import hr.perisic.luka.data.local.model.CurrencyModel
import hr.perisic.luka.data.remote.model.Sparkline
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

internal interface CurrencyService {

    @GET("currencies/ticker?sort=rank&interval=1d,7d,30d,365d,ytd")
    fun filterCurrencies(
        @Query("page") page: Int,
        @Query("per-page") limit: Int
    ): Single<List<Currency>>

    @GET("currencies/ticker?sort=rank&interval=1d")
    fun filterCurrencies(
        @Query("ids") ids: String,
        @Query("page") page: Int,
        @Query("per-page") limit: Int
    ): Single<List<Currency>>

    @GET("currencies/sparkline")
    fun getSparklines(
        @Query("start") start: String,
        @Query("end") end: String,
        @Query("ids") currency: String
    ): Single<List<Sparkline>>

    @GET("https://api.coingecko.com/api/v3/coins/list?include_platform=false")
    fun getAllCurrencies(): Single<List<CurrencyModel>>

}