package com.example.koshelek_ru_test.data.repository

import android.content.Context
import com.example.koshelek_ru_test.data.api.BinanceApiService
import com.example.koshelek_ru_test.data.vo.DiffOrder
import com.example.koshelek_ru_test.data.vo.JsonResponse
import com.example.koshelek_ru_test.data.vo.TradeOrder
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response

class DbRepository {
    val asksDiff = HashMap<Double, Double>()
    val tempAsksDiffOrder = mutableListOf<DiffOrder>()

    fun getAskTradeOrders(service: BinanceApiService, context: Context, callback: OnApiResponse) {
         val tempAsks = mutableListOf<TradeOrder>()
        service.getOrders().enqueue(object : retrofit2.Callback<JsonResponse> {
            override fun onResponse(
                call: Call<JsonResponse>,
                response: Response<JsonResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.apply {
                        this.asks.forEach {
                           val tradeOrder = convertJsonToTradeOrder(it[0], it[1])
                            tempAsks.add(tradeOrder)
                            val tempAmount = asksDiff[tradeOrder.price]
                            if(tempAmount!=null) asksDiff[tradeOrder.price] = tradeOrder.quantity - tempAmount
                        }
                        if (tempAsks.isEmpty()) {
                            callback.onResponseFailed("can't get data from api")
                        } else {
                            callback.onResponse(Single.create{
                                it.onSuccess(tempAsks.sortedBy { it.price })
                            })
                        }

                    }
                }
            }
            override fun onFailure(call: Call<JsonResponse>, t: Throwable) {
            }
        })
    }

    fun getBidTradeOrders(service: BinanceApiService, context: Context, callback: OnApiResponse) {
         val tempBids = mutableListOf<TradeOrder>()
        service.getOrders().enqueue(object : retrofit2.Callback<JsonResponse> {
            override fun onResponse(
                call: Call<JsonResponse>,
                response: Response<JsonResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.apply {
                        this.bids.forEach {
                           val tradeOrder = convertJsonToTradeOrder(it[0], it[1])
                            tempBids.add(tradeOrder)
                        }
                        if (tempBids.isEmpty()) {
                            callback.onResponseFailed("can't get data from api")
                        } else {
                            callback.onResponse(Single.create{
                                it.onSuccess(tempBids.sortedBy { it.price })
                            })
                        }

                    }
                }
            }
            override fun onFailure(call: Call<JsonResponse>, t: Throwable) {
            }
        })
    }

    fun getAskDiff(service: BinanceApiService, context: Context, callback: OnDiffApiResponse) {
        val tempAsks = mutableListOf<TradeOrder>()
        service.getOrders().enqueue(object : retrofit2.Callback<JsonResponse> {
            override fun onResponse(
                call: Call<JsonResponse>,
                response: Response<JsonResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.apply {
                        this.asks.forEach {
                            val tradeOrder = convertJsonToTradeOrder(it[0], it[1])
                            val tempAmount = asksDiff[tradeOrder.price]
                            if(tempAmount!=null) asksDiff[tradeOrder.price] = tradeOrder.quantity - tempAmount
                        }
                        asksDiff.forEach{
                            tempAsksDiffOrder.add(
                                DiffOrder(it.key, it.value)
                            )
                        }
                        if (tempAsks.isEmpty()) {
                            callback.onDiffResponseFail("can't get data from api")
                        } else {
                            callback.onDiffResponse(Single.create{
                                it.onSuccess(tempAsksDiffOrder)
                            })
                        }

                    }
                }
            }
            override fun onFailure(call: Call<JsonResponse>, t: Throwable) {
            }
        })
    }

    private fun convertJsonToTradeOrder(price: String, quantity: String) : TradeOrder {
        return TradeOrder (
            price.toDouble(),
            quantity.toDouble()
        )
    }

    interface OnApiResponse {
        fun onResponse (orders: Single<List<TradeOrder>>)
        fun onResponseFailed (error: String)

    }

    interface OnDiffApiResponse {
        fun onDiffResponse (orders: Single<List<DiffOrder>>)
        fun onDiffResponseFail (error: String)
    }
}