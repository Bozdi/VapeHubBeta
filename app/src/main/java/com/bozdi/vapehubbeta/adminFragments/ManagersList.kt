package com.bozdi.vapehubbeta.adminFragments

import android.os.Bundle
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
import com.bozdi.vapehubbeta.adapters.CouriersAdapter
import com.bozdi.vapehubbeta.adapters.ManagerActionListener
import com.bozdi.vapehubbeta.adapters.ManagersAdapter
import com.bozdi.vapehubbeta.adapters.OrderActionListener
import com.bozdi.vapehubbeta.managerFragments.OrderNew
import com.bozdi.vapehubbeta.managerFragments.OrderReviewManager
import com.bozdi.vapehubbeta.model.*
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ManagersList : Fragment() {
    private lateinit var adapter: ManagersAdapter

    private val managersListService: ManagersListService
        get() = (getActivity()?.getApplicationContext() as AppServices).managersService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val res = inflater.inflate(R.layout.fragment_managers_list, container, false)
        val rv: RecyclerView = res.findViewById(R.id.Managers_List)

        adapter = ManagersAdapter(object : ManagerActionListener {
            override fun onManagerClick(manager: ManagersData) {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container, ManagerReview(manager))
                    ?.addToBackStack(null)
                    ?.commit()
            }
        })

        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(activity)
        managersListService.addListener(managersListener)

        val addManagerButton : FloatingActionButton = res.findViewById(R.id.newManagerButton)
        addManagerButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, ManagerNew())
                ?.addToBackStack(null)
                ?.commit()
        }

        val refresh = res.findViewById<SwipeRefreshLayout>(R.id.refreshManagersList)

        refresh.setOnRefreshListener {
            Toast.makeText(activity,"Список обновлён", Toast.LENGTH_SHORT).show()
            (getActivity()?.getApplicationContext() as AppServices).serverData.getManagersList()
            adapter.notifyDataSetChanged()
            refresh.isRefreshing = false;
        }

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