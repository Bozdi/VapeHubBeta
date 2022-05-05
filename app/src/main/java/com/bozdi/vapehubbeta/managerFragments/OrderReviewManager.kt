package com.bozdi.vapehubbeta.managerFragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.bozdi.vapehubbeta.*
import com.bozdi.vapehubbeta.adminFragments.CitiesList
import com.bozdi.vapehubbeta.model.OrdersData

class OrderReviewManager(private var selectOrder: OrdersData) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).supportActionBar?.title = getString(R.string.OrderReview)
        val res = inflater.inflate(R.layout.fragment_manager_order_review, container, false)

        res.findViewById<TextView>(R.id.orderReviewNameET).setText(selectOrder.ClientName)
        res.findViewById<TextView>(R.id.orderReviewStreetET).setText(selectOrder.StreetName)
        res.findViewById<TextView>(R.id.orderReviewApartNumberET).setText(selectOrder.ApartNum)
        res.findViewById<TextView>(R.id.orderReviewBuildingNumberET).setText(selectOrder.BuildingNum)
        res.findViewById<TextView>(R.id.orderReviewStatusET).setText(selectOrder.Status)
        res.findViewById<TextView>(R.id.orderReviewPhoneNumberET).setText(selectOrder.ClientPhone)
        res.findViewById<TextView>(R.id.orderReviewEntranceET).setText(selectOrder.EntranceNum)


        res.findViewById<Button>(R.id.orderReviewEditButton).setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, OrderEdit(selectOrder))
                ?.addToBackStack(null)
                ?.commit()
        }

        res.findViewById<Button>(R.id.deleteOrderButton).setOnClickListener {
            if(selectOrder.Status == "PEND") {
                (activity?.applicationContext as AppServices).serverData.deleteOrder(

                    selectOrder.OrderId,

                    object : CreateOrderCallBack {
                        override fun onSuccess() {
                            (activity?.applicationContext as AppServices).ordersService.del(selectOrder)
                            (activity?.applicationContext as AppServices).serverData.getOrdersList()
                            activity?.supportFragmentManager?.beginTransaction()
                                ?.replace(R.id.fragment_container, OrdersList())
                                ?.addToBackStack(null)
                                ?.commit()
                        }

                        override fun onError(text: String) {
                            (activity?.applicationContext as AppServices).ordersService.del(selectOrder)
                            (activity?.applicationContext as AppServices).serverData.getOrdersList()
                            activity?.supportFragmentManager?.beginTransaction()
                                ?.replace(R.id.fragment_container, OrdersList())
                                ?.addToBackStack(null)
                                ?.commit()

                        }
                    }
                )
            } else {
                Toast.makeText(activity,"Невозможно удалить взятый на исполнение заказ", Toast.LENGTH_SHORT).show()
            }
        }
        return res

    }
}