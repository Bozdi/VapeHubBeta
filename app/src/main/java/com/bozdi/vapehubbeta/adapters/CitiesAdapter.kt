package com.bozdi.vapehubbeta.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bozdi.vapehubbeta.databinding.CityItemBinding
import com.bozdi.vapehubbeta.databinding.OrderItemBinding
import com.bozdi.vapehubbeta.model.CitiesData
import com.bozdi.vapehubbeta.model.OrdersData

interface CitiesActionListener {
    fun onCityClick(city: CitiesData)
}

class CitiesAdapter(private val actionListener: CitiesActionListener) : RecyclerView.Adapter<CitiesAdapter.CitiesViewHolder>(), View.OnClickListener {

    var citiesData:List<CitiesData> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onClick(v: View) {
        val city = v.tag as CitiesData
        actionListener.onCityClick(city)
    }

        class CitiesViewHolder(
        val binding: CityItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CityItemBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return CitiesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        val city = citiesData[position]
        with(holder.binding) {
            holder.itemView.tag = city
            cityNameTextView.text = city.Name
        }
    }

    override fun getItemCount(): Int = citiesData.size

}