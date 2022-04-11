package com.bozdi.vapehubbeta.managerFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bozdi.vapehubbeta.AppServices
import com.bozdi.vapehubbeta.adapters.CouriersAdapter
import com.bozdi.vapehubbeta.R
import com.bozdi.vapehubbeta.model.CouriersListService
import com.bozdi.vapehubbeta.model.couriersListener

class CouriersList : Fragment() {
    private lateinit var adapter: CouriersAdapter

    private val courierService: CouriersListService
        get() = (getActivity()?.getApplicationContext() as AppServices).couriersService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val res = inflater.inflate(R.layout.fragment_manager_couriers, container, false)
        var rv: RecyclerView = res.findViewById(R.id.Couriers_List)
        adapter = CouriersAdapter()
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(activity)
        courierService.addListener(couriersListener)
        return res
    }

    override fun onDestroy() {
        super.onDestroy()
        courierService.removeListener(couriersListener)
    }

    private val couriersListener: couriersListener ={
        adapter.couriers = it
    }


}