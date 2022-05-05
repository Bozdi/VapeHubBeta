package com.bozdi.vapehubbeta.adminFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.bozdi.vapehubbeta.*
import com.bozdi.vapehubbeta.managerFragments.OrderEdit
import com.bozdi.vapehubbeta.model.OrdersData
import com.bozdi.vapehubbeta.model.StoresData


class StoreReview(private var selectStore: StoresData, private var cityName: String) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).supportActionBar?.title = getString(R.string.StoreReview)
        val res = inflater.inflate(R.layout.fragment_store_review, container, false)

        res.findViewById<TextView>(R.id.storeReviewCityET).text = cityName
        res.findViewById<TextView>(R.id.storeReviewStreetET).text = selectStore.Street
        res.findViewById<TextView>(R.id.storeReviewBuildingNumET).text = selectStore.BuildingNumber


        res.findViewById<Button>(R.id.storeReviewEditButton).setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()
            (activity?.applicationContext as AppServices).serverData.getActualCitiesList(
                object : ActualCitiesListCallBack {

                    override fun onSuccess(ids: Array<String>, names: Array<String>) {
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, StoreEdit(
                                selectStore,
                                names,
                                ids
                            ))
                            ?.addToBackStack(null)
                            ?.commit()
                    }

                    override fun onError(text: String) {
                    }
                })
        }
        res.findViewById<Button>(R.id.deleteStoreButton).setOnClickListener {

            (getActivity()?.getApplicationContext() as AppServices).serverData.deleteStore(

                selectStore.StoreId.toString(),

                object : CreateOrderCallBack {
                    override fun onSuccess() {
                        (getActivity()?.getApplicationContext() as AppServices).storesService.del(selectStore)
                        (getActivity()?.getApplicationContext() as AppServices).serverData.getStoresList()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, StoresList())
                            ?.addToBackStack(null)
                            ?.commit()
                       }

                    override fun onError(text: String) {
                        (getActivity()?.getApplicationContext() as AppServices).storesService.del(selectStore)
                        (getActivity()?.getApplicationContext() as AppServices).serverData.getStoresList()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, StoresList())
                            ?.addToBackStack(null)
                            ?.commit()

                    }
                }
            )
        }

        return res

    }
}