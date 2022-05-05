package com.bozdi.vapehubbeta.courierFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.bozdi.vapehubbeta.AppServices
import com.bozdi.vapehubbeta.CreateOrderCallBack
import com.bozdi.vapehubbeta.OrdersList
import com.bozdi.vapehubbeta.R
import com.bozdi.vapehubbeta.managerFragments.OrderEdit
import com.bozdi.vapehubbeta.model.OrdersData

class OrderTaken(private var selectOrderCourier: OrdersData) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val res = inflater.inflate(R.layout.fragment_order_taken, container, false)

        res.findViewById<TextView>(R.id.orderTakenNameET).text = selectOrderCourier.ClientName
        res.findViewById<TextView>(R.id.orderTakenStreetET).text = selectOrderCourier.StreetName
        res.findViewById<TextView>(R.id.orderTakenApartNumberET).text = selectOrderCourier.ApartNum
        res.findViewById<TextView>(R.id.orderTakenBuildingNumberET).text = selectOrderCourier.BuildingNum
        res.findViewById<TextView>(R.id.orderTakenStatusET).text = selectOrderCourier.OrderId
        res.findViewById<TextView>(R.id.orderTakenPhoneNumberET).text = selectOrderCourier.ClientPhone
        res.findViewById<TextView>(R.id.orderTakenEntranceET).text = selectOrderCourier.EntranceNum


        res.findViewById<Button>(R.id.orderTakenFinishOrderButton).setOnClickListener {
            (activity?.applicationContext as AppServices).serverData.completeOrder(
                selectOrderCourier.OrderId, 1, 1,
                object : CreateOrderCallBack {
                    override fun onSuccess() {
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, OrdersList())
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