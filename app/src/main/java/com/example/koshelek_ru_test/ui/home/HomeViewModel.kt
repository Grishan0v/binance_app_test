package com.example.koshelek_ru_test.ui.home

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.koshelek_ru_test.data.repository.DbUseCase
import com.example.koshelek_ru_test.data.vo.TradeOrder
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val useCase: DbUseCase): ViewModel() {
    private lateinit var handler: Handler
    private val bidOrdersLiveData = MutableLiveData<List<TradeOrder>>()
    private val askOrdersLiveData = MutableLiveData<List<TradeOrder>>()
    private val errorLiveData = MutableLiveData<String>()

    val bids: LiveData<List<TradeOrder>>
        get() = bidOrdersLiveData

    val asks: LiveData<List<TradeOrder>>
        get() = askOrdersLiveData

    @SuppressLint("CheckResult")
    fun initAskOrdersList() {
    handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                useCase.getAskTradeOrders(object : DbUseCase.GetTradeOrdersCallback {
                    override fun onSuccess(orders: Single<List<TradeOrder>>) {
                        orders.subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ askOrdersLiveData.postValue(it) },
                                { errorLiveData.postValue("Error occurred :(") }
                            )
                    }
                    override fun onError(error: String) { errorLiveData.postValue(error) }
                    })

                handler.postDelayed(this, 1000)
                Log.d("API", "updating asks...")
                }
            })
    }

    @SuppressLint("CheckResult")
    fun initBidOrdersList() {
    handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                useCase.getBidTradeOrders(object : DbUseCase.GetTradeOrdersCallback {
                    override fun onSuccess(orders: Single<List<TradeOrder>>) {
                        orders.subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ bidOrdersLiveData.postValue(it) },
                                { errorLiveData.postValue("Error occurred :(") }
                            )
                    }
                    override fun onError(error: String) { errorLiveData.postValue(error) }
                    })

                handler.postDelayed(this, 1000)
                Log.d("API", "updating bids...")
                }
            })
    }

}