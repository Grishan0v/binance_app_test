package com.example.koshelek_ru_test.data.api

import com.example.koshelek_ru_test.data.vo.JsonResponse
import retrofit2.Call
import retrofit2.http.GET

interface BinanceApiService {

    @GET("depth")
    fun getOrders(): Call<JsonResponse>
}