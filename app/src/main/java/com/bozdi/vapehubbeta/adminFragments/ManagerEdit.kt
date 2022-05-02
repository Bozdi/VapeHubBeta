package com.bozdi.vapehubbeta.adminFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.bozdi.vapehubbeta.AppServices
import com.bozdi.vapehubbeta.CreateOrderCallBack
import com.bozdi.vapehubbeta.MainActivity
import com.bozdi.vapehubbeta.R
import com.bozdi.vapehubbeta.databinding.FragmentManagerEditBinding
import com.bozdi.vapehubbeta.databinding.FragmentOrderEditBinding
import com.bozdi.vapehubbeta.managerFragments.CouriersList
import com.bozdi.vapehubbeta.model.ManagersData
import com.bozdi.vapehubbeta.model.OrdersData
import com.bozdi.vapehubbeta.model.SelectedManagersStoreData

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
        (activity as MainActivity).supportActionBar?.title = getString(R.string.ManagerEdit)
        val res = inflater.inflate(R.layout.fragment_manager_edit, container, false)

        res.findViewById<TextView>(R.id.editManagerNameET).setText(selectManager.Name)
        res.findViewById<TextView>(R.id.editManagerLoginET).setText(selectManager.Login)
        res.findViewById<TextView>(R.id.editManagerPhoneNumberET).setText(selectManager.Phone)

        res.findViewById<Button>(R.id.editManagerSaveChangesButton).setOnClickListener {

            (getActivity()?.getApplicationContext() as AppServices).serverData.editUser(

                res.findViewById<EditText>(R.id.editManagerLoginET).text.toString(),
                res.findViewById<EditText>(R.id.editManagerPasswordET).text.toString(),
                res.findViewById<EditText>(R.id.editManagerNameET).text.toString(),
                res.findViewById<EditText>(R.id.editManagerPhoneNumberET).text.toString(),
                selectManager.UserId,

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