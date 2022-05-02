package com.bozdi.vapehubbeta.adminFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.bozdi.vapehubbeta.AppServices
import com.bozdi.vapehubbeta.CreateOrderCallBack
import com.bozdi.vapehubbeta.MainActivity
import com.bozdi.vapehubbeta.R
import com.bozdi.vapehubbeta.managerFragments.OrderEdit
import com.bozdi.vapehubbeta.model.OrdersData
import com.bozdi.vapehubbeta.model.StoresData


class StoreReview(private var selectStore: StoresData) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).supportActionBar?.title = getString(R.string.StoreReview)
        val res = inflater.inflate(R.layout.fragment_store_review, container, false)

        res.findViewById<TextView>(R.id.storeReviewCityET).setText(selectStore.CityId)
        res.findViewById<TextView>(R.id.storeReviewStreetET).setText(selectStore.Street)
        res.findViewById<TextView>(R.id.storeReviewBuildingNumET).setText(selectStore.BuildingNumber)


        res.findViewById<Button>(R.id.storeReviewEditButton).setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, StoreEdit(selectStore))
                ?.addToBackStack(null)
                ?.commit()
        }
        res.findViewById<Button>(R.id.deleteStoreButton).setOnClickListener {

            (getActivity()?.getApplicationContext() as AppServices).serverData.deleteStore(

                selectStore.StoreId.toString(),

                object : CreateOrderCallBack {
                    override fun onSuccess() {
                        (getActivity()?.getApplicationContext() as AppServices).serverData.getStoresList()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, StoresList())
                            ?.addToBackStack(null)
                            ?.commit()
                       }

                    override fun onError(text: String) {
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