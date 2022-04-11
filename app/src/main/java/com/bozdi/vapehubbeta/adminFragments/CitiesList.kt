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
import com.bozdi.vapehubbeta.adapters.CitiesAdapter
import com.bozdi.vapehubbeta.adapters.CouriersAdapter
import com.bozdi.vapehubbeta.model.CitiesListService
import com.bozdi.vapehubbeta.model.CouriersListService
import com.bozdi.vapehubbeta.model.citiesListener

class CitiesList : Fragment() {
    private lateinit var adapter: CitiesAdapter

    private val citiesListService: CitiesListService
        get() = (getActivity()?.getApplicationContext() as AppServices).citiesService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val res = inflater.inflate(R.layout.fragment_cities_list, container, false)
        var rv: RecyclerView = res.findViewById(R.id.Cities_List)
        adapter = CitiesAdapter()
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(activity)
        citiesListService.addListener(citiesListener)
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