package com.bozdi.vapehubbeta.managerFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bozdi.vapehubbeta.ActualCitiesListCallBack
import com.bozdi.vapehubbeta.AppServices
import com.bozdi.vapehubbeta.adapters.CouriersAdapter
import com.bozdi.vapehubbeta.R
import com.bozdi.vapehubbeta.adapters.CouriersActionListener
import com.bozdi.vapehubbeta.model.CouriersData
import com.bozdi.vapehubbeta.model.CouriersListService
import com.bozdi.vapehubbeta.model.couriersListener
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CouriersList : Fragment() {
    private lateinit var adapter: CouriersAdapter

    private val courierService: CouriersListService
        get() = (getActivity()?.getApplicationContext() as AppServices).couriersService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val res = inflater.inflate(R.layout.fragment_manager_couriers, container, false)
        val rv: RecyclerView = res.findViewById(R.id.Couriers_List)

        adapter = CouriersAdapter(object : CouriersActionListener {
            override fun onCourierClick(courier: CouriersData) {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container, ManagerCourierReview(courier))
                    ?.addToBackStack(null)
                    ?.commit()
            }
        })

        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(activity)
        courierService.addListener(couriersListener)

        val addCourierButton : FloatingActionButton = res.findViewById(R.id.newCourierButton)
        addCourierButton.setOnClickListener {
            (getActivity()?.getApplicationContext() as AppServices).serverData.getActualCitiesList(
                object : ActualCitiesListCallBack {

                    override fun onSuccess(ids: Array<String>, names: Array<String>) {
                        var StoresIds = mutableListOf<String>();
                        var StoresNames = mutableListOf<String>();
                        var stores = (getActivity()?.getApplicationContext() as AppServices).storesService.getStores()
                        stores.forEach {
                            StoresIds.add(it.StoreId.toString())
                            StoresNames.add(it.Street.toString() + " " + it.BuildingNumber.toString())
                        }

                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, CourierNew(
                                ids,
                                names,
                                StoresIds.toTypedArray(),
                                StoresNames.toTypedArray()))
                            ?.addToBackStack(null)
                            ?.commit()
                    }

                    override fun onError(text: String) {
                    }
            })
    }

    val refresh = res.findViewById<SwipeRefreshLayout>(R.id.refreshCouriersList)

    refresh.setOnRefreshListener {
        Toast.makeText(activity,"Список обновлён",Toast.LENGTH_SHORT).show()
        (getActivity()?.getApplicationContext() as AppServices).serverData.getCouriersList()
        adapter.notifyDataSetChanged()
        refresh.isRefreshing = false;
    }

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