package com.glob.movies.services

import com.glob.movies.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("api_key", BuildConfig.api_key)
            .build()

        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}
