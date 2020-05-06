package com.glob.movies.services

import com.glob.movies.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitFactory {

    companion object {
        //todo This code neer a refactor to be a single object
        fun getInstance(): Retrofit {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            return Retrofit.Builder()
                .client(getClient())
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }

        private fun getClient(): OkHttpClient {
            val httpLoggingInterceptor =
                HttpLoggingInterceptor().setLevel(
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else
                        HttpLoggingInterceptor.Level.NONE
                )

            return OkHttpClient().newBuilder() //httpLogging interceptor for logging network requests
                .addInterceptor(AuthInterceptor())
                .addInterceptor(ContentTypeInterceptor())
                .addInterceptor(httpLoggingInterceptor) //Encryption interceptor for encryption of request data
                .build()
        }
    }
}