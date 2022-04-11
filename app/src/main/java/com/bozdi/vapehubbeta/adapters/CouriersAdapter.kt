package com.bozdi.vapehubbeta.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bozdi.vapehubbeta.databinding.CourierItemBinding
import com.bozdi.vapehubbeta.model.CouriersData

class CouriersAdapter : RecyclerView.Adapter<CouriersAdapter.CouriersViewHolder>() {

    var couriers:List<CouriersData> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class CouriersViewHolder(
        val binding: CourierItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouriersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CourierItemBinding.inflate(inflater, parent, false)
        return CouriersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CouriersViewHolder, position: Int) {
        val courier = couriers[position]
        with(holder.binding) {
            courierNameTextView.text = courier.Name
            courierStatusTextView.text = courier.Status
        }
    }

    override fun getItemCount(): Int = couriers.size

}