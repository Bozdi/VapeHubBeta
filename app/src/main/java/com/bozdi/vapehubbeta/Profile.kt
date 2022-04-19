package com.bozdi.vapehubbeta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class Profile : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val res =  inflater.inflate(R.layout.fragment_profile, container, false)

        res.findViewById<TextView>(R.id.profileLoginTV).setText(GlobalVars.Login)

        return res;
    }

}