package com.bozdi.vapehubbeta

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bozdi.vapehubbeta.databinding.OrderItemBinding
import com.bozdi.vapehubbeta.model.Order

class OrdersAdapter :RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {

    var orders:List<Order> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class OrderViewHolder(
        val binding: OrderItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = OrderItemBinding.inflate(inflater, parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        with(holder.binding) {
            orderIdTextView.text = order.OrderId
            orderAdressTextView.text = order.From
            orderStatusTextView.text = order.Status
            orderTotalCostTextView.text = order.TotalCost
        }
    }

    override fun getItemCount(): Int = orders.size

}