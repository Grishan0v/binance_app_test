package com.example.koshelek_ru_test.ui.diff

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.koshelek_ru_test.R
import com.example.koshelek_ru_test.data.App
import com.example.koshelek_ru_test.data.vo.DiffOrder
import com.example.koshelek_ru_test.ui.DiffRecycleAdapter

class DiffAskTableFragment : Fragment() {
    private var adapter: DiffRecycleAdapter? = null
    private var viewModel: DiffViewModel? = null
    private val orders = mutableListOf<DiffOrder>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_diff_ask_table, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        initRecycleView()
        viewModel = DiffViewModel(App.instance.useCase)
        viewModel!!.getAsksDiff()
        viewModel!!.diffAsks.observe(this.viewLifecycleOwner, Observer { item -> adapter?.setItems(item) })
    }

//    @SuppressLint("ResourceAsColor")
//    private fun initRecycleView() {
//        adapter = DiffRecycleAdapter(orders)
//        recycler_view_diff_asks.adapter = adapter
//    }
}