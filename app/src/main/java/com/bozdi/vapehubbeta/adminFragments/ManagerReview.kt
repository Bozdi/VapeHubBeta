package com.bozdi.vapehubbeta.adminFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.bozdi.vapehubbeta.AppServices
import com.bozdi.vapehubbeta.CreateOrderCallBack
import com.bozdi.vapehubbeta.R
import com.bozdi.vapehubbeta.ServerData
import com.bozdi.vapehubbeta.managerFragments.OrderEdit
import com.bozdi.vapehubbeta.model.*

class ManagerReview(private var selectManager: ManagersData) : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val res = inflater.inflate(R.layout.fragment_manager_review, container, false)

        res.findViewById<TextView>(R.id.managerReviewNameET).setText(selectManager.Name)
        res.findViewById<TextView>(R.id.managerReviewLoginET).setText(selectManager.Login)
        res.findViewById<TextView>(R.id.managerReviewStoreAddressET).setText("")
        res.findViewById<TextView>(R.id.managerReviewPhoneNumberET).setText(selectManager.Phone)

        
        res.findViewById<Button>(R.id.managerReviewEditButton).setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, ManagerEdit(selectManager))
                ?.addToBackStack(null)
                ?.commit()
        }
        res.findViewById<Button>(R.id.deleteManagerButton).setOnClickListener {

            (getActivity()?.getApplicationContext() as AppServices).serverData.deleteUser(

                selectManager.UserId.toString(),

                object : CreateOrderCallBack {
                    override fun onSuccess() {
                        (getActivity()?.getApplicationContext() as AppServices).serverData.getManagersList()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, ManagersList())
                            ?.addToBackStack(null)
                            ?.commit()
                        }

                    override fun onError(text: String) {
                        (getActivity()?.getApplicationContext() as AppServices).serverData.getManagersList()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, ManagersList())
                            ?.addToBackStack(null)
                            ?.commit()

                    }
                }
            )
        }

        return res

    }
}