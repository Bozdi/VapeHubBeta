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
import com.bozdi.vapehubbeta.adapters.ManagersAdapter
import com.bozdi.vapehubbeta.model.CouriersListService
import com.bozdi.vapehubbeta.model.ManagersListService
import com.bozdi.vapehubbeta.model.managersListener

class ManagersList : Fragment() {
    private lateinit var adapter: ManagersAdapter

    private val managersListService: ManagersListService
        get() = (getActivity()?.getApplicationContext() as AppServices).managersService


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val res = inflater.inflate(R.layout.fragment_managers_list, container, false)
        var rv: RecyclerView = res.findViewById(R.id.Managers_List)
        adapter = ManagersAdapter()
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(activity)
        managersListService.addListener(managersListener)
        return res
    }

    override fun onDestroy() {
        super.onDestroy()
        managersListService.removeListener(managersListener)
    }

    private val managersListener: managersListener ={
        adapter.managersData = it
    }


}