package com.bozdi.vapehubbeta.adminFragments

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
import com.bozdi.vapehubbeta.AppServices
import com.bozdi.vapehubbeta.MainActivity
import com.bozdi.vapehubbeta.R
import com.bozdi.vapehubbeta.adapters.*
import com.bozdi.vapehubbeta.managerFragments.OrderNew
import com.bozdi.vapehubbeta.model.*
import com.google.android.material.floatingactionbutton.FloatingActionButton

class StoresList : Fragment() {
    private lateinit var adapter: StoresAdapter

    private val storesService: StoresListService
        get() = (getActivity()?.getApplicationContext() as AppServices).storesService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        (activity as MainActivity).supportActionBar?.title = getString(R.string.StoresList)
        val res = inflater.inflate(R.layout.fragment_stores_list, container, false)
        val rv: RecyclerView = res.findViewById(R.id.Stores_List)

        adapter = StoresAdapter(object : StoreActionListener {
            override fun onStoreClick(store: StoresData) {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container, StoreReview(store))
                    ?.addToBackStack(null)
                    ?.commit()
            }
        })

        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(activity)
        storesService.addListener(storesListener)

        val addStoreButton : FloatingActionButton = res.findViewById(R.id.newStoreButton)
        addStoreButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, StoreNew())
                ?.addToBackStack(null)
                ?.commit()
        }
        (getActivity()?.getApplicationContext() as AppServices).serverData.getStoresList()
        Handler().postDelayed({
            adapter.notifyDataSetChanged()
        }, 500)

        val refresh = res.findViewById<SwipeRefreshLayout>(R.id.refresh_Stores_List)
        refresh.setOnRefreshListener {
            (getActivity()?.getApplicationContext() as AppServices).serverData.getStoresList()
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
        storesService.removeListener(storesListener)
    }

    private val storesListener: storesListener ={
        adapter.storesData = it
    }


}