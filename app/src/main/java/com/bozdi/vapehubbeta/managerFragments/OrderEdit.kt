package com.bozdi.vapehubbeta.managerFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.TextView
import com.bozdi.vapehubbeta.OrdersList
import com.bozdi.vapehubbeta.R
import com.bozdi.vapehubbeta.databinding.ActivityLoginBinding.inflate
import com.bozdi.vapehubbeta.databinding.CourierGoodItemBinding.inflate
import com.bozdi.vapehubbeta.databinding.FragmentOrderEditBinding
import com.bozdi.vapehubbeta.model.OrdersData

class OrderEdit(private var selectOrder: OrdersData) : Fragment() {
    private lateinit var binding: FragmentOrderEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentOrderEditBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val res = inflater.inflate(R.layout.fragment_order_edit, container, false)

        res.findViewById<TextView>(R.id.editOrderNameET).setText(selectOrder.ClientName)
        res.findViewById<TextView>(R.id.editOrderPhoneNumberET).setText(selectOrder.ClientPhone)
        res.findViewById<TextView>(R.id.editOrderBuildingNumberET).setText(selectOrder.BuildingNum)
        res.findViewById<TextView>(R.id.editOrderStreetNameET).setText(selectOrder.StreetName)
        res.findViewById<TextView>(R.id.editOrderApartNumET).setText(selectOrder.ApartNum)
        res.findViewById<TextView>(R.id.editOrderEntranceNumET).setText(selectOrder.EntranceNum)

        return res

    }


}