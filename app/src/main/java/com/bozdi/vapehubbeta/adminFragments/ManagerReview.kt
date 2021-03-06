package com.bozdi.vapehubbeta.adminFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import com.bozdi.vapehubbeta.*
import com.bozdi.vapehubbeta.model.*

class ManagerReview(private var selectManager: ManagersData,private var street: String ) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container, ManagersList())
                    ?.addToBackStack(null)
                    ?.commit()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).supportActionBar?.title = "Менеджер " + selectManager.Name
        val res = inflater.inflate(R.layout.fragment_manager_review, container, false)

        res.findViewById<TextView>(R.id.managerReviewNameET).text = selectManager.Name
        res.findViewById<TextView>(R.id.managerReviewLoginET).text = selectManager.Login
        res.findViewById<TextView>(R.id.managerReviewStoreAddressET).text = street
        res.findViewById<TextView>(R.id.managerReviewPhoneNumberET).text = selectManager.Phone


        val editManagerButton : Button = res.findViewById(R.id.managerReviewEditButton)
        editManagerButton.setOnClickListener {
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
                            ?.replace(R.id.fragment_container, ManagerEdit(
                                ids,
                                names,
                                storesIds.toTypedArray(),
                                storesNames.toTypedArray(),
                                selectManager
                            ))
                            ?.addToBackStack(null)
                            ?.commit()
                    }

                    override fun onError(text: String) {
                    }
                })
        }
        res.findViewById<Button>(R.id.deleteManagerButton).setOnClickListener {

            (activity?.applicationContext as AppServices).serverData.deleteUser(

                selectManager.UserId,

                object : CreateOrderCallBack {
                    override fun onSuccess() {
                        (activity?.applicationContext as AppServices).managersService.del(selectManager)
                        (activity?.applicationContext as AppServices).serverData.getManagersList()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, ManagersList())
                            ?.addToBackStack(null)
                            ?.commit()
                        }

                    override fun onError(text: String) {
                        (activity?.applicationContext as AppServices).managersService.del(selectManager)
                        (activity?.applicationContext as AppServices).serverData.getManagersList()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, ManagersList())
                            ?.addToBackStack(null)
                            ?.commit()

                    }
                }
            )
        }

        return res

    }
}