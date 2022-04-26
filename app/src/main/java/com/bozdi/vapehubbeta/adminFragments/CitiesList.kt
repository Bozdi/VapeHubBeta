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
import com.bozdi.vapehubbeta.R
import com.bozdi.vapehubbeta.adapters.CitiesActionListener
import com.bozdi.vapehubbeta.adapters.CitiesAdapter
import com.bozdi.vapehubbeta.adapters.CouriersAdapter
import com.bozdi.vapehubbeta.adapters.OrderActionListener
import com.bozdi.vapehubbeta.managerFragments.OrderNew
import com.bozdi.vapehubbeta.managerFragments.OrderReviewManager
import com.bozdi.vapehubbeta.model.*
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CitiesList : Fragment() {
    private lateinit var adapter: CitiesAdapter

    private val citiesListService: CitiesListService
        get() = (getActivity()?.getApplicationContext() as AppServices).citiesService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val res = inflater.inflate(R.layout.fragment_cities_list, container, false)
        val rv: RecyclerView = res.findViewById(R.id.Cities_List)

        adapter = CitiesAdapter(object : CitiesActionListener {
            override fun onCityClick(city: CitiesData) {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container, CityReview(city))
                    ?.addToBackStack(null)
                    ?.commit()
            }
        })

        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(activity)
        citiesListService.addListener(citiesListener)

        val addCityButton : FloatingActionButton = res.findViewById(R.id.newCityButton)
        addCityButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, CityNew())
                ?.addToBackStack(null)
                ?.commit()
        }
        (getActivity()?.getApplicationContext() as AppServices).serverData.getCitiesList()
        Handler().postDelayed({
            adapter.notifyDataSetChanged()
        }, 500)

        val refresh = res.findViewById<SwipeRefreshLayout>(R.id.refreshCitiesList)
        refresh.setOnRefreshListener {
            (getActivity()?.getApplicationContext() as AppServices).serverData.getCitiesList()
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
        citiesListService.removeListener(citiesListener)
    }

    private val citiesListener: citiesListener ={
        adapter.citiesData = it
    }


}