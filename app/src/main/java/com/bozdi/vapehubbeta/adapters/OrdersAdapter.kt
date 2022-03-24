package com.bozdi.vapehubbeta.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bozdi.vapehubbeta.R
import com.bozdi.vapehubbeta.databinding.OrderItemBinding
import com.bozdi.vapehubbeta.recyclerData.orderData

class OrdersAdapter: RecyclerView.Adapter<OrdersAdapter.OrdersHolder>() {

    val orderList = ArrayList<orderData>()
    class OrdersHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = OrderItemBinding.bind(item)
        fun bind(orderData: orderData) = with(binding) {
            orderIdTextView.text = orderData.OrderId
            orderAdressTextView.text = orderData.StreetName
            orderStatusTextView.text = orderData.Status
            orderTotalCostTextView.text = orderData.TotalCost

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_item, parent, false)
        return OrdersHolder(view)
    }

    override fun onBindViewHolder(holder: OrdersHolder, position: Int) {
        holder.bind(orderList[position])
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
    fun addOrder(orderData: orderData){
        orderList.add(orderData)
        notifyDataSetChanged()
    }
}