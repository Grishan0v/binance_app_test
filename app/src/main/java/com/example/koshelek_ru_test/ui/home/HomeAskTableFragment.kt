package com.example.koshelek_ru_test.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.koshelek_ru_test.R
import com.example.koshelek_ru_test.data.App
import com.example.koshelek_ru_test.data.vo.TradeOrder
import com.example.koshelek_ru_test.ui.RecyclerAdapter
import kotlinx.android.synthetic.main.fragment_home_asks_table.*


class HomeAskTableFragment : Fragment() {
    private var adapter: RecyclerAdapter? = null
    private var viewModel: HomeViewModel? = null
    private val orders = mutableListOf<TradeOrder>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_asks_table, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView()
        viewModel = HomeViewModel(App.instance.useCase)
        viewModel!!.initAskOrdersList()
        viewModel!!.asks.observe(this.viewLifecycleOwner, Observer { item -> adapter?.setItems(item) })
    }

    @SuppressLint("ResourceAsColor")
    private fun initRecycleView() {
        adapter = RecyclerAdapter(orders)
        recycler_view_home_asks.adapter = adapter
    }
}