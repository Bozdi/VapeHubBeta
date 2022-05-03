package com.bozdi.vapehubbeta.courierFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.bozdi.vapehubbeta.OrdersList
import com.bozdi.vapehubbeta.R
import com.bozdi.vapehubbeta.managerFragments.OrderEdit
import com.bozdi.vapehubbeta.model.OrdersData

class OrderTaken(private var selectOrder: OrdersData) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val res = inflater.inflate(R.layout.fragment_order_taken, container, false)

        res.findViewById<TextView>(R.id.orderTakenNameET).text = selectOrder.ClientName
        res.findViewById<TextView>(R.id.orderTakenStreetET).text = selectOrder.StreetName
        res.findViewById<TextView>(R.id.orderTakenApartNumberET).text = selectOrder.ApartNum
        res.findViewById<TextView>(R.id.orderTakenBuildingNumberET).text = selectOrder.BuildingNum
        res.findViewById<TextView>(R.id.orderTakenStatusET).text = selectOrder.Status
        res.findViewById<TextView>(R.id.orderTakenPhoneNumberET).text = selectOrder.ClientPhone
        res.findViewById<TextView>(R.id.orderTakenEntranceET).text = selectOrder.EntranceNum


        res.findViewById<Button>(R.id.orderTakenFinishOrderButton).setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, OrdersList())
                ?.addToBackStack(null)
                ?.commit()
        }

        return res

    }
}