package com.bozdi.vapehubbeta

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bozdi.vapehubbeta.adapters.OrderActionListener
import com.bozdi.vapehubbeta.adapters.OrdersAdapter
import com.bozdi.vapehubbeta.courierFragments.OrderReviewCourier
import com.bozdi.vapehubbeta.courierFragments.OrderTaken
import com.bozdi.vapehubbeta.managerFragments.OrderNew
import com.bozdi.vapehubbeta.managerFragments.OrderReviewManager
import com.bozdi.vapehubbeta.model.OrderListService
import com.bozdi.vapehubbeta.model.OrdersData
import com.bozdi.vapehubbeta.model.ordersListener
import com.google.android.material.floatingactionbutton.FloatingActionButton

class OrdersList : Fragment() {
    private lateinit var adapter: OrdersAdapter


    private val orderService: OrderListService
        get() = (getActivity()?.getApplicationContext() as AppServices).ordersService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val res = inflater.inflate(R.layout.fragment_manager_orders, container, false)
        val rv: RecyclerView = res.findViewById(R.id.Order_List)

        adapter = OrdersAdapter(object : OrderActionListener {
            override fun onOrderClick(order: OrdersData) {
                if(GlobalVars.UserType == "MNGR") {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.fragment_container, OrderReviewManager(order))
                        ?.addToBackStack(null)
                        ?.commit()
                } else {
                    if(order.Status == "Ожидает") {
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, OrderReviewCourier(order))
                            ?.addToBackStack(null)
                            ?.commit()
                    } else {
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, OrderTaken(order))
                            ?.addToBackStack(null)
                            ?.commit()
                    }

                }

            }
        })

        (activity as MainActivity).supportActionBar?.title = getString(R.string.Orders)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(activity)
        orderService.addListener(orderLister)

        val addButton : FloatingActionButton = res.findViewById(R.id.newOrderButton)
        addButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, OrderNew())
                ?.addToBackStack(null)
                ?.commit()
        }
//        (getActivity()?.getApplicationContext() as AppServices).serverData.getOrdersList()
//        Handler().postDelayed({
//            adapter.notifyDataSetChanged()
//        }, 10000)

        val refresh = res.findViewById<SwipeRefreshLayout>(R.id.refresh_Order_List)

        refresh.setOnRefreshListener {
            (getActivity()?.getApplicationContext() as AppServices).serverData.getOrdersList()
            Handler().postDelayed({
                adapter.notifyDataSetChanged()
                Toast.makeText(activity,"Список обновлён", Toast.LENGTH_SHORT).show()
            }, 500)
            refresh.isRefreshing = false
        }

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