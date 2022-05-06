package com.bozdi.vapehubbeta

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.coroutines.Dispatchers.Main

class Profile : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("test", GlobalVars.Login)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.Profile)
        val res =  inflater.inflate(R.layout.fragment_profile, container, false)

        res.findViewById<TextView>(R.id.profileLoginTV).text = GlobalVars.Login
        res.findViewById<TextView>(R.id.profileNameTV).text = GlobalVars.ProfileName
        res.findViewById<TextView>(R.id.profilePhoneNumberTV).text = GlobalVars.ProfilePhoneNumber
        res.findViewById<TextView>(R.id.profileStoreNameTV).text = GlobalVars.ProfileStoreName

        res.findViewById<Button>(R.id.profileEditButton).setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, ProfileEdit())
                ?.addToBackStack(null)
                ?.commit()
        }

        res.findViewById<Button>(R.id.profileExitButton).setOnClickListener {
            activity?.let{
                val intent = Intent (it, LoginActivity::class.java)
                it.startActivity(intent)
            }

        }


        return res
    }

}