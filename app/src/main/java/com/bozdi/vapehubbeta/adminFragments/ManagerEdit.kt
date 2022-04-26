package com.bozdi.vapehubbeta.adminFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bozdi.vapehubbeta.R
import com.bozdi.vapehubbeta.databinding.FragmentManagerEditBinding
import com.bozdi.vapehubbeta.databinding.FragmentOrderEditBinding
import com.bozdi.vapehubbeta.model.ManagersData
import com.bozdi.vapehubbeta.model.OrdersData

class ManagerEdit(private var selectManager: ManagersData) : Fragment() {
    private lateinit var binding: FragmentManagerEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentManagerEditBinding.inflate(layoutInflater)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val res = inflater.inflate(R.layout.fragment_manager_edit, container, false)

        res.findViewById<TextView>(R.id.editManagerNameET).setText(selectManager.Name)
        res.findViewById<TextView>(R.id.editManagerLoginET).setText(selectManager.Login)
        res.findViewById<TextView>(R.id.editManagerStoreAddressET).setText("Казахстан 70")
        res.findViewById<TextView>(R.id.editManagerPhoneNumberET).setText(selectManager.Phone)

        return res

    }

}