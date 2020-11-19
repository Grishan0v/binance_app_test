package com.example.koshelek_ru_test.ui.diff

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.koshelek_ru_test.data.repository.DbUseCase
import com.example.koshelek_ru_test.data.vo.DiffOrder
import com.example.koshelek_ru_test.data.vo.TradeOrder
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DiffViewModel(private val useCase: DbUseCase): ViewModel() {
    private lateinit var handler: Handler
    private val bidDiffLiveData = MutableLiveData<List<DiffOrder>>()
    private val askDiffLiveData = MutableLiveData<List<DiffOrder>>()
    private val errorLiveData = MutableLiveData<String>()

    val diffBids: LiveData<List<DiffOrder>>
        get() = bidDiffLiveData

    val diffAsks: LiveData<List<DiffOrder>>
        get() = askDiffLiveData

    fun getAsksDiff(){
        handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                useCase.getDiffAsks(object : DbUseCase.GetDiffCallback {
                    @SuppressLint("CheckResult")
                    override fun onSuccess(diff: Single<List<DiffOrder>>) {
                        diff.subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ askDiffLiveData.postValue(it) },
                                { errorLiveData.postValue("Error occurred :(") }
                            )
                    }
                    override fun onError(error: String) { errorLiveData.postValue(error) }
                })

                handler.postDelayed(this, 1000)
                Log.d("API", "updating asksDiff...")
            }
        })
    }
}