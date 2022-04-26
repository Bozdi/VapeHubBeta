package com.bozdi.vapehubbeta.managerFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.bozdi.vapehubbeta.R
import com.bozdi.vapehubbeta.model.CouriersData
import com.bozdi.vapehubbeta.model.OrdersData

class ManagerCourierReview(private var selectCourier: CouriersData) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val res = inflater.inflate(R.layout.fragment_manager_courier_review, container, false)

        res.findViewById<TextView>(R.id.courierReviewNameET).setText(selectCourier.Name)
        res.findViewById<TextView>(R.id.courierReviewLoginET).setText(selectCourier.Login)
        res.findViewById<TextView>(R.id.courierReviewPhoneNumberET).setText(selectCourier.Phone)

        res.findViewById<Button>(R.id.courierReviewEditButton).setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, CourierEdit(selectCourier))
                ?.addToBackStack(null)
                ?.commit()
        }

        return res

    }
}