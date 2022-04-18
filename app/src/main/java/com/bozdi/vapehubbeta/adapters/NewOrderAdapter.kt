package com.bozdi.vapehubbeta.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bozdi.vapehubbeta.databinding.CourierGoodItemBinding
import com.bozdi.vapehubbeta.model.GoodsDialogData

class NewOrderAdapter : RecyclerView.Adapter<NewOrderAdapter.GoodsDialogViewHolder>() {

    var goodsData:List<GoodsDialogData> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class GoodsDialogViewHolder(
        val binding: CourierGoodItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsDialogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CourierGoodItemBinding.inflate(inflater, parent, false)
        return GoodsDialogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GoodsDialogViewHolder, position: Int) {
        val good = goodsData[position]
        with(holder.binding) {
            courierGoodsNameTextView.text = good.Name
            courierGoodsQuantityTextView.text = good.Available
        }
    }

    override fun getItemCount(): Int = goodsData.size

}
