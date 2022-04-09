package com.bozdi.vapehubbeta

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bozdi.vapehubbeta.adapters.OrdersAdapter
import com.bozdi.vapehubbeta.model.OrderListService
import com.bozdi.vapehubbeta.model.ordersListener


class OrdersList : Fragment() {
    private lateinit var adapter: OrdersAdapter

    private val orderService: OrderListService
        get() = (getActivity()?.getApplicationContext() as AppServices).ordersService

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val res = inflater.inflate(R.layout.fragment_manager_orders, container, false)
        var rv: RecyclerView = res.findViewById(R.id.Order_List);
        adapter = OrdersAdapter();
        rv.adapter = adapter;
        rv.layoutManager = LinearLayoutManager(activity)
        orderService.addListener(orderLister)
        return res
    }

    override fun onDestroy() {
        super.onDestroy()
        orderService.removeListener(orderLister)
    }

    private val orderLister: ordersListener ={
        adapter.ordersData = it
    }


}