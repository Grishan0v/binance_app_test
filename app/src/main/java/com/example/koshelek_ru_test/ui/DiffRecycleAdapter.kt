package com.example.koshelek_ru_test.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.koshelek_ru_test.R
import com.example.koshelek_ru_test.data.vo.DiffOrder
import kotlinx.android.synthetic.main.order_diff_item.view.*

class DiffRecycleAdapter(private val orders: MutableList<DiffOrder>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return OrdersViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.order_diff_item,
                parent,
                false
            )
        )
    }

    fun setItems(orderList: List<DiffOrder>) {
        orders.clear()
        orders.addAll(orderList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is OrdersViewHolder ->
                holder.bind(orders[position])
        }
    }

    class OrdersViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val diffPrice: TextView = itemView.diff_price
        private val diffAmount: TextView = itemView.diff_amount

        fun bind (order: DiffOrder) {
            diffPrice.text = order.price.toString()
            diffAmount.text = order.diff.toString()
        }
    }

}