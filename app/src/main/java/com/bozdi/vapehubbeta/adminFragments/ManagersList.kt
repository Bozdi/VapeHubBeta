package com.bozdi.vapehubbeta.adminFragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bozdi.vapehubbeta.*
import com.bozdi.vapehubbeta.adapters.ManagerActionListener
import com.bozdi.vapehubbeta.adapters.ManagersAdapter
import com.bozdi.vapehubbeta.model.ManagersData
import com.bozdi.vapehubbeta.model.ManagersListService
import com.bozdi.vapehubbeta.model.managersListener
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ManagersList : Fragment() {
    private lateinit var adapter: ManagersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }
        })
    }

    private val managersListService: ManagersListService
        get() = (activity?.applicationContext as AppServices).managersService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        (activity as MainActivity).supportActionBar?.title = getString(R.string.Managers)
        val res = inflater.inflate(R.layout.fragment_managers_list, container, false)
        val rv: RecyclerView = res.findViewById(R.id.Managers_List)

        adapter = ManagersAdapter(object : ManagerActionListener {
            override fun onManagerClick(manager: ManagersData) {
                (activity?.applicationContext as AppServices).serverData.getStoreData(manager.StoreId,
                    object : StorageDateListCallBack {
                        override fun onSuccess(street: String) {
                            activity?.supportFragmentManager?.beginTransaction()
                                ?.replace(R.id.fragment_container, ManagerReview(manager, street))
                                ?.addToBackStack(null)
                                ?.commit()
                        }
                        override fun onError(text: String) {
                            TODO("Not yet implemented")
                        }
                    }
                )
            }
        })

        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(activity)
        managersListService.addListener(managersListener)

        val addManagerButton : FloatingActionButton = res.findViewById(R.id.newManagerButton)
        addManagerButton.setOnClickListener {
            (activity?.applicationContext as AppServices).serverData.getActualCitiesList(
                object : ActualCitiesListCallBack {

                    override fun onSuccess(ids: Array<String>, names: Array<String>) {
                        val storesIds = mutableListOf<String>()
                        val storesNames = mutableListOf<String>()
                        val stores = (activity?.applicationContext as AppServices).storesService.getStores()
                        stores.forEach {
                            storesIds.add(it.StoreId)
                            storesNames.add(it.Street.toString() + " " + it.BuildingNumber.toString())
                        }

                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, ManagerNew(
                                ids,
                                names,
                                storesIds.toTypedArray(),
                                storesNames.toTypedArray()
                            ))
                            ?.addToBackStack(null)
                            ?.commit()
                    }

                    override fun onError(text: String) {
                    }
                })
        }
        (activity?.applicationContext as AppServices).serverData.getManagersList()
        Handler().postDelayed({
            adapter.notifyDataSetChanged()
        }, 500)

        val refresh = res.findViewById<SwipeRefreshLayout>(R.id.refreshManagersList)

        refresh.setOnRefreshListener {
            (activity?.applicationContext as AppServices).serverData.getManagersList()
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
        managersListService.removeListener(managersListener)
    }



    private val managersListener: managersListener ={
        adapter.managersData = it
    }


}