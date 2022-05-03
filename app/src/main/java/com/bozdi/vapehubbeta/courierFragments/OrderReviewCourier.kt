package com.bozdi.vapehubbeta.courierFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.bozdi.vapehubbeta.*
import com.bozdi.vapehubbeta.managerFragments.OrderEdit
import com.bozdi.vapehubbeta.model.OrdersData

class OrderReviewCourier(private var selectOrderCourier: OrdersData) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val res = inflater.inflate(R.layout.fragment_order_review_courier, container, false)

        res.findViewById<TextView>(R.id.orderReviewCourierNameET).text = selectOrderCourier.ClientName
        res.findViewById<TextView>(R.id.orderReviewCourierStreetET).text = selectOrderCourier.StreetName
        res.findViewById<TextView>(R.id.orderReviewCourierApartNumberET).text = selectOrderCourier.ApartNum
        res.findViewById<TextView>(R.id.orderReviewCourierBuildingNumberET).text = selectOrderCourier.BuildingNum
        res.findViewById<TextView>(R.id.orderReviewCourierStatusET).text = selectOrderCourier.Status
        res.findViewById<TextView>(R.id.orderReviewCourierPhoneNumberET).text = selectOrderCourier.ClientPhone
        res.findViewById<TextView>(R.id.orderReviewCourierEntranceET).text = selectOrderCourier.EntranceNum


        res.findViewById<Button>(R.id.orderReviewCourierTakeOrderButton).setOnClickListener{
            (activity?.applicationContext as AppServices).serverData.acceptOrder(selectOrderCourier.OrderId,
                object : CreateOrderCallBack {
                    override fun onSuccess() {
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, OrderTaken(selectOrderCourier))
                            ?.addToBackStack(null)
                            ?.commit()
                    }

                    override fun onError(text: String) {
                    }

                })

        }

        return res

    }
}