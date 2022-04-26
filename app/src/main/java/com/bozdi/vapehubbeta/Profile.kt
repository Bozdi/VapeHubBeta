package com.bozdi.vapehubbeta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.bozdi.vapehubbeta.managerFragments.CourierEdit
import com.bozdi.vapehubbeta.model.CouriersData

class Profile : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val res =  inflater.inflate(R.layout.fragment_profile, container, false)

        res.findViewById<TextView>(R.id.profileLoginTV).setText(GlobalVars.Login)
        res.findViewById<TextView>(R.id.profileNameTV).setText(GlobalVars.ProfileName)
        res.findViewById<TextView>(R.id.profilePhoneNumberTV).setText(GlobalVars.ProfilePhoneNumber)
        res.findViewById<TextView>(R.id.profileNameTV).setText(GlobalVars.Login)

        res.findViewById<Button>(R.id.profileEditButton).setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, ProfileEdit())
                ?.addToBackStack(null)
                ?.commit()
        }

        return res;
    }

}