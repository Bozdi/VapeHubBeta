package com.bozdi.vapehubbeta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bozdi.vapehubbeta.model.CouriersData

class ProfileEdit : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val res = inflater.inflate(R.layout.fragment_profile_edit, container, false)

        res.findViewById<TextView>(R.id.editProfileLoginET).setText(GlobalVars.Login)
        res.findViewById<TextView>(R.id.editProfileNameET).setText(GlobalVars.ProfileName)
        res.findViewById<TextView>(R.id.editProfilePhoneNumberET).setText(GlobalVars.ProfilePhoneNumber)
        //res.findViewById<TextView>(R.id.editManagerStoreAddressET).setText("Казахстан 70")

        return res

    }
}