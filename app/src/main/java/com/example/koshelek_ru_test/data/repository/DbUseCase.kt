package com.example.koshelek_ru_test.data.repository

import android.app.Application
import com.example.koshelek_ru_test.data.api.BinanceApiService
import com.example.koshelek_ru_test.data.vo.DiffOrder
import com.example.koshelek_ru_test.data.vo.TradeOrder
import io.reactivex.Single

class DbUseCase(
    private val application: Application,
    private val service: BinanceApiService,
    private val repository: DbRepository) {

    fun getAskTradeOrders(callback: GetTradeOrdersCallback) {
        repository.getAskTradeOrders(service,application,object :DbRepository.OnApiResponse {
            override fun onResponse(orders: Single<List<TradeOrder>>) {
                callback.onSuccess(orders)
            }

            override fun onResponseFailed(error: String) {
                callback.onError(error)
            }
        })

    }

    fun getBidTradeOrders(callback: GetTradeOrdersCallback) {
        repository.getBidTradeOrders(service,application,object :DbRepository.OnApiResponse {
            override fun onResponse(orders: Single<List<TradeOrder>>) {
                callback.onSuccess(orders)
            }

            override fun onResponseFailed(error: String) {
                callback.onError(error)
            }
        })

    }

    fun getDiffAsks(callback: GetDiffCallback) {
        repository.getAskDiff(service,application,object :DbRepository.OnDiffApiResponse {
            override fun onDiffResponse(orders: Single<List<DiffOrder>>) {
                callback.onSuccess(orders)
            }

            override fun onDiffResponseFail(error: String) {
                callback.onError(error)
            }
        })
    }


    interface GetTradeOrdersCallback {
        fun onSuccess(orders: Single<List<TradeOrder>>)
        fun onError(error: String)
    }

    interface GetDiffCallback {
        fun onSuccess(diff: Single<List<DiffOrder>>)
        fun onError(error: String)
    }
}