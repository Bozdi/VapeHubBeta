package com.bozdi.vapehubbeta.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bozdi.vapehubbeta.R
import com.bozdi.vapehubbeta.databinding.SelectedGoodItemBinding
import com.bozdi.vapehubbeta.model.GoodsData
interface SelectedGoodsActionListener {
    fun onPlusArturPidorClick(good: GoodsData)
    fun onMinusArturSinSobakyClick(good: GoodsData)
}
class SelectedGoodsAdapter(private val actionListener: SelectedGoodsActionListener) : RecyclerView.Adapter<SelectedGoodsAdapter.SelectedGoodsViewHolder>(), View.OnClickListener   {

    var goodsData:List<GoodsData> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class SelectedGoodsViewHolder(
        val binding: SelectedGoodItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedGoodsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SelectedGoodItemBinding.inflate(inflater, parent, false)
        binding.plusSelectedGood.setOnClickListener(this)
        binding.minusSelectedGood.setOnClickListener(this)
        return SelectedGoodsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectedGoodsViewHolder, position: Int) {
        val good = goodsData[position]
        with(holder.binding) {
            plusSelectedGood.tag = good
            minusSelectedGood.tag = good
            courierGoodsNameTextView.text = good.Name
            courierGoodsQuantityTextView.text = good.Available
        }
    }

    override fun getItemCount(): Int = goodsData.size
    override fun onClick(v: View) {
       val good = v.tag as GoodsData
        when (v.id) {
            R.id.minusSelectedGood -> {
                actionListener.onMinusArturSinSobakyClick(good)
            }
            R.id.plusSelectedGood -> {
                actionListener.onPlusArturPidorClick(good)
            }
        }

    }

}