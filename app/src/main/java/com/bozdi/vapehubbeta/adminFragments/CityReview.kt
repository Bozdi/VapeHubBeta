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
import com.bozdi.vapehubbeta.model.CitiesData
import com.bozdi.vapehubbeta.model.OrdersData

class CityReview(private var selectCity: CitiesData) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val res = inflater.inflate(R.layout.fragment_city_review, container, false)

        res.findViewById<TextView>(R.id.cityReviewNameET).setText(selectCity.Name)


        res.findViewById<Button>(R.id.cityReviewEditButton).setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, CityEdit(selectCity))
                ?.addToBackStack(null)
                ?.commit()
        }

        return res

    }

}