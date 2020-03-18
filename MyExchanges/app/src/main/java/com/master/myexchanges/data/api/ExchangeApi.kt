package com.master.myexchanges.data.api

import com.master.myexchanges.BuildConfig
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object ExchangeApi {
    private const val TIMEOUT_SECONDS: Long = 120
    private var retrofit: Retrofit? = null

    private val client: Retrofit
        get() {
            if (retrofit == null) {
                val httpClient = OkHttpClient.Builder()
                httpClient.readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                httpClient.callTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)


                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                httpClient.addInterceptor(logging)

                retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build()
            }
            return retrofit as Retrofit
        }

    fun <S> createService(serviceClass: Class<S>): S {
        return client.create(serviceClass)
    }
}