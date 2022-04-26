package com.bozdi.vapehubbeta.courierFragments

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

class OrderReviewCourier(private var selectOrderCourier: OrdersData) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val res = inflater.inflate(R.layout.fragment_order_review_courier, container, false)

        res.findViewById<TextView>(R.id.orderReviewCourierNameET).setText(selectOrderCourier.ClientName)
        res.findViewById<TextView>(R.id.orderReviewCourierStreetET).setText(selectOrderCourier.StreetName)
        res.findViewById<TextView>(R.id.orderReviewCourierApartNumberET).setText(selectOrderCourier.ApartNum)
        res.findViewById<TextView>(R.id.orderReviewCourierBuildingNumberET).setText(selectOrderCourier.BuildingNum)
        res.findViewById<TextView>(R.id.orderReviewCourierStatusET).setText(selectOrderCourier.Status)
        res.findViewById<TextView>(R.id.orderReviewCourierPhoneNumberET).setText(selectOrderCourier.ClientPhone)
        res.findViewById<TextView>(R.id.orderReviewCourierEntranceET).setText(selectOrderCourier.EntranceNum)


        res.findViewById<Button>(R.id.orderReviewCourierTakeOrderButton).setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, OrderTaken(selectOrderCourier))
                ?.addToBackStack(null)
                ?.commit()
        }

        return res

    }
}