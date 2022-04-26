package com.bozdi.vapehubbeta.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bozdi.vapehubbeta.databinding.CourierItemBinding
import com.bozdi.vapehubbeta.databinding.StoreItemBinding
import com.bozdi.vapehubbeta.model.CouriersData
import com.bozdi.vapehubbeta.model.OrdersData
import com.bozdi.vapehubbeta.model.StoresData

interface StoreActionListener {
    fun onStoreClick(store: StoresData)
}

class StoresAdapter(private val actionListener: StoreActionListener) : RecyclerView.Adapter<StoresAdapter.StoresViewHolder>(), View.OnClickListener {

    var storesData:List<StoresData> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onClick(v: View) {
        val order = v.tag as StoresData
        actionListener.onStoreClick(order)
    }

    class StoresViewHolder(
        val binding: StoreItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoresViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = StoreItemBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return StoresViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoresViewHolder, position: Int) {
        val store = storesData[position]
        with(holder.binding) {
            holder.itemView.tag = store
            storeStreetTextView.text = store.Street
            storeBuildingNumTextView.text = store.BuildingNumber
        }
    }

    override fun getItemCount(): Int = storesData.size

}