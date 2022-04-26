package com.bozdi.vapehubbeta.adminFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.bozdi.vapehubbeta.AppServices
import com.bozdi.vapehubbeta.R
import com.bozdi.vapehubbeta.managerFragments.OrderEdit
import com.bozdi.vapehubbeta.model.ManagersData
import com.bozdi.vapehubbeta.model.OrdersData

class ManagerReview(private var selectManager: ManagersData) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val res = inflater.inflate(R.layout.fragment_manager_review, container, false)
        var citie = (getActivity()?.getApplicationContext() as AppServices).storesService.getCitiesNameForCitiesId(selectManager.StoreId.toString());

        res.findViewById<TextView>(R.id.managerReviewNameET).setText(selectManager.Name)
        res.findViewById<TextView>(R.id.managerReviewLoginET).setText(selectManager.Login)
        res.findViewById<TextView>(R.id.managerReviewStoreAddressET).setText(citie)
        res.findViewById<TextView>(R.id.managerReviewPhoneNumberET).setText(selectManager.Phone)

        
        res.findViewById<Button>(R.id.managerReviewEditButton).setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, ManagerEdit(selectManager))
                ?.addToBackStack(null)
                ?.commit()
        }

        return res

    }
}