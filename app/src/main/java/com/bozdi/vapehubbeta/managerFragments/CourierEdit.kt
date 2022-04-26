package com.bozdi.vapehubbeta.managerFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bozdi.vapehubbeta.R
import com.bozdi.vapehubbeta.databinding.FragmentCourierEditBinding
import com.bozdi.vapehubbeta.databinding.FragmentManagerEditBinding
import com.bozdi.vapehubbeta.model.CouriersData
import com.bozdi.vapehubbeta.model.ManagersData

class CourierEdit(private var selectCourier: CouriersData) : Fragment() {
    private lateinit var binding: FragmentCourierEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCourierEditBinding.inflate(layoutInflater)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val res = inflater.inflate(R.layout.fragment_courier_edit, container, false)

        res.findViewById<TextView>(R.id.editCourierNameET).setText(selectCourier.Name)
        res.findViewById<TextView>(R.id.editCourierLoginET).setText(selectCourier.Login)
        res.findViewById<TextView>(R.id.editCourierStoreAddressET).setText("Казахстан 70")
        res.findViewById<TextView>(R.id.editCourierPhoneNumberET).setText(selectCourier.Phone)

        return res

    }

}