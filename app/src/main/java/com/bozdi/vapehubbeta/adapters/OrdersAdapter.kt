package com.bozdi.vapehubbeta.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bozdi.vapehubbeta.databinding.OrderItemBinding
import com.bozdi.vapehubbeta.model.OrdersData

class OrdersAdapter :RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {

    var ordersData:List<OrdersData> = emptyList()
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
        val order = ordersData[position]
        with(holder.binding) {
            orderIdTextView.text = order.OrderId
            orderStreetNameTextView.text = order.StreetName
            orderBuildingNumTextView.text = order.BuildingNum
            orderStatusTextView.text = order.Status
            orderTotalCostTextView.text = order.TotalCost
        }
    }

    override fun getItemCount(): Int = ordersData.size

}