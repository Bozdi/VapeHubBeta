package com.bozdi.vapehubbeta.adminFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.bozdi.vapehubbeta.R
import com.bozdi.vapehubbeta.managerFragments.OrderEdit
import com.bozdi.vapehubbeta.model.OrdersData
import com.bozdi.vapehubbeta.model.StoresData


class StoreReview(private var selectStore: StoresData) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        return res

    }
}