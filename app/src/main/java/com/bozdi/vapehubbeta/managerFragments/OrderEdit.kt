package com.bozdi.vapehubbeta.managerFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.bozdi.vapehubbeta.*
import com.bozdi.vapehubbeta.adminFragments.ManagersList
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
        (activity as MainActivity).supportActionBar?.title = getString(R.string.OrderEdit)
        val res = inflater.inflate(R.layout.fragment_order_edit, container, false)

        res.findViewById<TextView>(R.id.editOrderNameET).text = selectOrder.ClientName
        res.findViewById<TextView>(R.id.editOrderPhoneNumberET).text = selectOrder.ClientPhone
        res.findViewById<TextView>(R.id.editOrderBuildingNumberET).text = selectOrder.BuildingNum
        res.findViewById<TextView>(R.id.editOrderStreetNameET).text = selectOrder.StreetName
        res.findViewById<TextView>(R.id.editOrderApartNumET).text = selectOrder.ApartNum
        res.findViewById<TextView>(R.id.editOrderEntranceNumET).text = selectOrder.EntranceNum

        res.findViewById<Button>(R.id.editOrderSaveChangesButton).setOnClickListener {

            (activity?.applicationContext as AppServices).serverData.editOrder(

                res.findViewById<EditText>(R.id.editOrderNameET).text.toString(),
                res.findViewById<EditText>(R.id.editOrderPhoneNumberET).text.toString(),
                res.findViewById<EditText>(R.id.editOrderStreetNameET).text.toString(),
                res.findViewById<EditText>(R.id.editOrderBuildingNumberET).text.toString(),
                res.findViewById<EditText>(R.id.editOrderApartNumET).text.toString(),
                res.findViewById<EditText>(R.id.editOrderEntranceNumET).text.toString(),
                selectOrder.OrderId,

                object : CreateOrderCallBack {
                    override fun onSuccess() {
                        (activity?.applicationContext as AppServices).serverData.getOrdersList()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, OrdersList())
                            ?.addToBackStack(null)
                            ?.commit()
                    }

                    override fun onError(text: String) {
                        (activity?.applicationContext as AppServices).serverData.getOrdersList()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, OrdersList())
                            ?.addToBackStack(null)
                            ?.commit()
                    }
                }
            )

        }

        return res

    }


}