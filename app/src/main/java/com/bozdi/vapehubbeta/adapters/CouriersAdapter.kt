package com.bozdi.vapehubbeta.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bozdi.vapehubbeta.databinding.CourierItemBinding
import com.bozdi.vapehubbeta.model.CouriersData

interface CouriersActionListener{
    fun onCourierClick(courier: CouriersData)
}

class CouriersAdapter(private val actionListener: CouriersActionListener) : RecyclerView.Adapter<CouriersAdapter.CouriersViewHolder>(), View.OnClickListener {

    var couriers:List<CouriersData> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onClick(v: View) {
        actionListener.onCourierClick(v.tag as CouriersData)
    }

    class CouriersViewHolder(
        val binding: CourierItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouriersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CourierItemBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return CouriersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CouriersViewHolder, position: Int) {
        val courier = couriers[position]
        with(holder.binding) {
            holder.itemView.tag = courier
            courierNameTextView.text = courier.Name
            if(courier.Status == "WAIT") {
                courierStatusTextView.text = "Ожидает"
            } else {
                courierStatusTextView.text = "На доставке"
            }

        }
    }

    override fun getItemCount(): Int = couriers.size


}