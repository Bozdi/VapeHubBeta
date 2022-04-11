package com.bozdi.vapehubbeta.adminFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bozdi.vapehubbeta.AppServices
import com.bozdi.vapehubbeta.R
import com.bozdi.vapehubbeta.adapters.CouriersAdapter
import com.bozdi.vapehubbeta.adapters.OrdersAdapter
import com.bozdi.vapehubbeta.adapters.StoresAdapter
import com.bozdi.vapehubbeta.model.CouriersListService
import com.bozdi.vapehubbeta.model.StoresListService
import com.bozdi.vapehubbeta.model.storesListener

class StoresList : Fragment() {
    private lateinit var adapter: StoresAdapter

    private val storesService: StoresListService
        get() = (getActivity()?.getApplicationContext() as AppServices).storesService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val res = inflater.inflate(R.layout.fragment_shops_list, container, false)
        var rv: RecyclerView = res.findViewById(R.id.Stores_List)
        adapter = StoresAdapter()
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(activity)
        storesService.addListener(storesListener)
        return res
    }

    override fun onDestroy() {
        super.onDestroy()
        storesService.removeListener(storesListener)
    }

    private val storesListener: storesListener ={
        adapter.storesData = it
    }


}