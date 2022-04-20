package com.bozdi.vapehubbeta.managerFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.bozdi.vapehubbeta.R
import com.bozdi.vapehubbeta.model.OrdersData

class OrderReviewManager(public var selectOrder: OrdersData) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val res = inflater.inflate(R.layout.fragment_manager_order_review, container, false)

        res.findViewById<TextView>(R.id.orderReviewNameET).setText(selectOrder.ClientName)
        res.findViewById<TextView>(R.id.orderReviewStreetET).setText(selectOrder.StreetName)

        res.findViewById<Button>(R.id.orderReviewEditButton).setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, OrderEdit(selectOrder))
                ?.addToBackStack(null)
                ?.commit()
        }

        return res

    }
}