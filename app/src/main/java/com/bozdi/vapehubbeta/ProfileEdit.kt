package com.bozdi.vapehubbeta

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.bozdi.vapehubbeta.adminFragments.ManagersList
import com.bozdi.vapehubbeta.model.CouriersData

class ProfileEdit : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val res = inflater.inflate(R.layout.fragment_profile_edit, container, false)

        res.findViewById<TextView>(R.id.editProfileLoginET).text = GlobalVars.Login
        res.findViewById<TextView>(R.id.editProfileNameET).text = GlobalVars.ProfileName
        res.findViewById<TextView>(R.id.editProfilePhoneNumberET).text = GlobalVars.ProfilePhoneNumber

        res.findViewById<Button>(R.id.editProfileSaveChangesButton).setOnClickListener {

            (getActivity()?.getApplicationContext() as AppServices).serverData.editUser(

                res.findViewById<EditText>(R.id.editProfileLoginET).text.toString(),
                res.findViewById<EditText>(R.id.editProfilePasswordET).text.toString(),
                res.findViewById<EditText>(R.id.editProfileNameET).text.toString(),
                res.findViewById<EditText>(R.id.editProfilePhoneNumberET).text.toString(),
                GlobalVars.StoreId.toString(),
                GlobalVars.UserId,

                object : CreateUserCallBack {
                    override fun onSuccess() {
                        (getActivity()?.getApplicationContext() as AppServices).serverData.getUserType(
                            object : GetUserDataCallBack {
                            override fun onSuccess() {


                            }

                            override fun onError(text: String) {
                                TODO("Not yet implemented")
                            }


                        })
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, Profile())
                            ?.addToBackStack(null)
                            ?.commit()
                    }

                    override fun onError(text: String) {

                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, Profile())
                            ?.addToBackStack(null)
                            ?.commit()
                    }
                }
            )

        }
        return res

    }
}