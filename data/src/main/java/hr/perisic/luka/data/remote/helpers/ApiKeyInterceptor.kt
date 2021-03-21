package hr.perisic.luka.data.remote.helpers

import hr.perisic.luka.base.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val requestUrl = request.url
        request = request.newBuilder().apply {
            url(
                requestUrl.newBuilder()
                    .addQueryParameter("key", BuildConfig.API_KEY)
                    .build()
            )
        }.build()
        return chain.proceed(request)
    }

}