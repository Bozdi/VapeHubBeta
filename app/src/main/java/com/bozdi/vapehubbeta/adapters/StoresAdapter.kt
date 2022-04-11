package com.bozdi.vapehubbeta.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bozdi.vapehubbeta.databinding.CourierItemBinding
import com.bozdi.vapehubbeta.databinding.StoreItemBinding
import com.bozdi.vapehubbeta.model.CouriersData
import com.bozdi.vapehubbeta.model.StoresData

class StoresAdapter : RecyclerView.Adapter<StoresAdapter.StoresViewHolder>() {

    var storesData:List<StoresData> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class StoresViewHolder(
        val binding: StoreItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoresViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = StoreItemBinding.inflate(inflater, parent, false)
        return StoresViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoresViewHolder, position: Int) {
        val store = storesData[position]
        with(holder.binding) {
            storeStreetTextView.text = store.Street
            storeBuildingNumTextView.text = store.BuildingNumber
        }
    }

    override fun getItemCount(): Int = storesData.size

}