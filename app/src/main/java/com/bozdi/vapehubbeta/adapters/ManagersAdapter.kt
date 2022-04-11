package com.bozdi.vapehubbeta.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bozdi.vapehubbeta.databinding.ManagerItemBinding
import com.bozdi.vapehubbeta.databinding.StoreItemBinding
import com.bozdi.vapehubbeta.model.ManagersData
import com.bozdi.vapehubbeta.model.StoresData

class ManagersAdapter : RecyclerView.Adapter<ManagersAdapter.ManagersViewHolder>() {

    var managersData:List<ManagersData> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class ManagersViewHolder(
        val binding: ManagerItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManagersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ManagerItemBinding.inflate(inflater, parent, false)
        return ManagersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ManagersViewHolder, position: Int) {
        val manager = managersData[position]
        with(holder.binding) {
            managerNameTextView.text = manager.Name
        }
    }

    override fun getItemCount(): Int = managersData.size

}