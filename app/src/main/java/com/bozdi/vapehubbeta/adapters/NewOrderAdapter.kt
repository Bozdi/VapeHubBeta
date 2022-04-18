package com.bozdi.vapehubbeta.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bozdi.vapehubbeta.databinding.CourierGoodItemBinding
import com.bozdi.vapehubbeta.model.GoodsData
import com.bozdi.vapehubbeta.model.OrdersData

interface NewOrderActionListener {
    fun onGoodClick(good: GoodsData)
}

class NewOrderAdapter(private val actionListener: NewOrderActionListener) : RecyclerView.Adapter<NewOrderAdapter.GoodsDialogViewHolder>(), View.OnClickListener {

    var goodsData:List<GoodsData> = emptyList()
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
        binding.root.setOnClickListener(this)
        return GoodsDialogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GoodsDialogViewHolder, position: Int) {
        val good = goodsData[position]
        with(holder.binding) {
            holder.itemView.tag = good
            courierGoodsNameTextView.text = good.Name
            courierGoodsQuantityTextView.text = good.Available
        }
    }

    override fun getItemCount(): Int = goodsData.size
    override fun onClick(v: View) {
        val good = v.tag as GoodsData
        actionListener.onGoodClick(good);
    }

}
