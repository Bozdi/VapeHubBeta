package com.bozdi.vapehubbeta.managerFragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bozdi.vapehubbeta.*
import com.bozdi.vapehubbeta.adapters.CouriersAdapter
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

        (activity as MainActivity).supportActionBar?.title = getString(R.string.Couriers)
        val res = inflater.inflate(R.layout.fragment_manager_couriers, container, false)
        val rv: RecyclerView = res.findViewById(R.id.Couriers_List)

        adapter = CouriersAdapter(object : CouriersActionListener {
            override fun onCourierClick(courier: CouriersData) {
                (activity?.applicationContext as AppServices).serverData.getStoreData(courier.StoreId,
                    object : StorageDateListCallBack {
                        override fun onSuccess(Street: String) {
                            activity?.supportFragmentManager?.beginTransaction()
                                ?.replace(R.id.fragment_container, ManagerCourierReview(courier, Street))
                                ?.addToBackStack(null)
                                ?.commit()
                        }

                        override fun onError(text: String) {
                            TODO("Not yet implemented")                    }


                    }
                )

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
        (getActivity()?.getApplicationContext() as AppServices).serverData.getCouriersList()
        Handler().postDelayed({
            adapter.notifyDataSetChanged()
        }, 500)

    val refresh = res.findViewById<SwipeRefreshLayout>(R.id.refreshCouriersList)

    refresh.setOnRefreshListener {
        (getActivity()?.getApplicationContext() as AppServices).serverData.getCouriersList()
        Handler().postDelayed({
            adapter.notifyDataSetChanged()
            Toast.makeText(activity,"Список обновлён", Toast.LENGTH_SHORT).show()
        }, 500)
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