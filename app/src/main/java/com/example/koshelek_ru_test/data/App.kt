package com.example.koshelek_ru_test.data

import android.app.Application
import com.example.koshelek_ru_test.data.api.BinanceApiService
import com.example.koshelek_ru_test.data.repository.DbRepository
import com.example.koshelek_ru_test.data.repository.DbUseCase
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class App: Application() {

    private lateinit var interceptor: Interceptor
    private lateinit var httpClient: OkHttpClient
    private lateinit var retrofit: Retrofit
    private lateinit var binanceApiService: BinanceApiService
    lateinit var useCase: DbUseCase
    private lateinit var repository: DbRepository

    override fun onCreate() {
        super.onCreate()
        instance =this
        interceptor = initInterceptor()
        httpClient = initHttpClient(interceptor)
        retrofit = initRetrofit(httpClient)
        binanceApiService = initNetworkService(retrofit)
        repository = DbRepository()
        useCase = DbUseCase(this, binanceApiService, repository)


    }

    private fun initInterceptor() : Interceptor =
        Interceptor { chain ->
            val url = chain.request()
                .url
                .newBuilder()
                .addQueryParameter("symbol", symbol)
                .addQueryParameter("limit", "100")
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            chain.proceed(request)
        }

    private fun initHttpClient(interceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

    private fun initRetrofit(client: OkHttpClient) : Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private fun initNetworkService(retrofit: Retrofit): BinanceApiService =
        retrofit.create(BinanceApiService::class.java)

    companion object{
        const val BASE_URL = "https://api.binance.com/api/v3/"
        lateinit var instance: App
        var symbol: String = "BTCUSDT"
    }
}