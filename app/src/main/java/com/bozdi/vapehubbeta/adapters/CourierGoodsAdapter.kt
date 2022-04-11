package com.bozdi.vapehubbeta.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bozdi.vapehubbeta.databinding.CityItemBinding
import com.bozdi.vapehubbeta.databinding.CourierGoodItemBinding
import com.bozdi.vapehubbeta.model.CitiesData
import com.bozdi.vapehubbeta.model.CourierGoodsData

class CourierGoodsAdapter : RecyclerView.Adapter<CourierGoodsAdapter.CourierGoodsViewHolder>() {

    var goodsData:List<CourierGoodsData> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class CourierGoodsViewHolder(
        val binding: CourierGoodItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourierGoodsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CourierGoodItemBinding.inflate(inflater, parent, false)
        return CourierGoodsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourierGoodsViewHolder, position: Int) {
        val good = goodsData[position]
        with(holder.binding) {
            courierGoodsNameTextView.text = good.Name
            courierGoodsQuantityTextView.text = good.Available
        }
    }

    override fun getItemCount(): Int = goodsData.size

}
