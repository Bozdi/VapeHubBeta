package com.bozdi.vapehubbeta.adminFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bozdi.vapehubbeta.MainActivity
import com.bozdi.vapehubbeta.R
import com.bozdi.vapehubbeta.databinding.FragmentStoreEditBinding

import com.bozdi.vapehubbeta.model.CouriersData
import com.bozdi.vapehubbeta.model.StoresData

class StoreEdit(private var selectStore: StoresData) : Fragment() {
    private lateinit var binding: FragmentStoreEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentStoreEditBinding.inflate(layoutInflater)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).supportActionBar?.title = getString(R.string.StoreEdit)
        val res = inflater.inflate(R.layout.fragment_store_edit, container, false)

        res.findViewById<TextView>(R.id.editStoreCityET).text = "112"
        res.findViewById<TextView>(R.id.editStoreStreetET).text = selectStore.Street
        res.findViewById<TextView>(R.id.editStoreBuildingNumET).text = selectStore.BuildingNumber

        return res

    }

}