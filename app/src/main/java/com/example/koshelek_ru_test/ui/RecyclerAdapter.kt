package com.example.koshelek_ru_test.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.koshelek_ru_test.R
import com.example.koshelek_ru_test.data.vo.TradeOrder
import kotlinx.android.synthetic.main.order_item.view.*

class RecyclerAdapter(private val orders: MutableList<TradeOrder>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return OrdersViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.order_item,
                parent,
                false
            )
        )
    }

    fun setItems(orderList: List<TradeOrder>) {
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
        private val orderAmount: TextView = itemView.item_amount
        private val orderPrice: TextView = itemView.item_price
        private val orderTotal: TextView = itemView.item_total

        fun bind (tradeOrder: TradeOrder) {
            orderAmount.text = String.format("%.6f",  tradeOrder.quantity)
            orderPrice.text = String.format("%.6f",  tradeOrder.price)
            orderTotal.text = String.format("%.6f",  tradeOrder.total)
        }
    }

}