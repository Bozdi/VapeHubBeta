package com.bozdi.vapehubbeta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.bozdi.vapehubbeta.courierFragments.OrderTaken
import com.bozdi.vapehubbeta.model.OrdersData

class CompletedOrder(private var completedOrder: OrdersData) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val res = inflater.inflate(R.layout.fragment_completed_order, container, false)

        (activity as MainActivity).supportActionBar?.title = "Заказ №" + completedOrder.OrderId

        res.findViewById<TextView>(R.id.completedOrderNameET).text = completedOrder.ClientName
        res.findViewById<TextView>(R.id.completedOrderStreetET).text = completedOrder.StreetName
        res.findViewById<TextView>(R.id.completedOrderApartNumberET).text = completedOrder.ApartNum
        res.findViewById<TextView>(R.id.completedOrderBuildingNumberET).text = completedOrder.BuildingNum
        res.findViewById<TextView>(R.id.completedOrderStatusET).text = completedOrder.Status
        res.findViewById<TextView>(R.id.completedOrderPhoneNumberET).text = completedOrder.ClientPhone
        res.findViewById<TextView>(R.id.completedOrderEntranceET).text = completedOrder.EntranceNum
        res.findViewById<TextView>(R.id.completedOrderDeliveryCostET).text = completedOrder.DeliveryCost
        res.findViewById<TextView>(R.id.completedOrderGoodsCostET).text = completedOrder.GoodsTotalCost
        res.findViewById<TextView>(R.id.completedOrderTotalCostET).text = completedOrder.TotalCost
        res.findViewById<TextView>(R.id.completedOrderCardPaymentET).text = completedOrder.CardPayment
        res.findViewById<TextView>(R.id.completedOrderCashPaymentET).text = completedOrder.CashPayment

        return res

    }

}