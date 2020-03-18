package com.master.myexchanges.data.api

import com.master.myexchanges.data.model.ExchangeRateData
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeService{

    @GET("latest")
    suspend fun getExchangeRate(@Query("base") source : String, @Query("symbols") destination : String): ExchangeRateData
}