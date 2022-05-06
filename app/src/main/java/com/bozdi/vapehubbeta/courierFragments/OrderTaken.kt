package com.bozdi.vapehubbeta.courierFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.bozdi.vapehubbeta.*
import com.bozdi.vapehubbeta.model.OrdersData

class OrderTaken(private var selectOrderCourier: OrdersData) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val res = inflater.inflate(R.layout.fragment_order_taken, container, false)

        (activity as MainActivity).supportActionBar?.title = "Заказ №" + selectOrderCourier.OrderId

        res.findViewById<TextView>(R.id.orderTakenNameET).text = selectOrderCourier.ClientName
        res.findViewById<TextView>(R.id.orderTakenStreetET).text = selectOrderCourier.StreetName
        res.findViewById<TextView>(R.id.orderTakenApartNumberET).text = selectOrderCourier.ApartNum
        res.findViewById<TextView>(R.id.orderTakenBuildingNumberET).text = selectOrderCourier.BuildingNum
        res.findViewById<TextView>(R.id.orderTakenStatusET).text = selectOrderCourier.Status
        res.findViewById<TextView>(R.id.orderTakenPhoneNumberET).text = selectOrderCourier.ClientPhone
        res.findViewById<TextView>(R.id.orderTakenEntranceET).text = selectOrderCourier.EntranceNum
        res.findViewById<TextView>(R.id.orderTakenDeliveryCostET).text = selectOrderCourier.DeliveryCost
        res.findViewById<TextView>(R.id.orderTakenGoodsCostET).text = selectOrderCourier.GoodsTotalCost
        res.findViewById<TextView>(R.id.orderTakenTotalCostET).text = selectOrderCourier.TotalCost


        res.findViewById<Button>(R.id.orderTakenFinishOrderButton).setOnClickListener {
            (activity?.applicationContext as AppServices).serverData.completeOrder(
                selectOrderCourier.OrderId,
                res.findViewById<EditText>(R.id.totalSumCashET).text.toString(),
                res.findViewById<EditText>(R.id.totalSumCardET).text.toString(),
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