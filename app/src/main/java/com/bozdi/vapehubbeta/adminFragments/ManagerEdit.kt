package com.bozdi.vapehubbeta.adminFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.bozdi.vapehubbeta.AppServices
import com.bozdi.vapehubbeta.CreateOrderCallBack
import com.bozdi.vapehubbeta.R
import com.bozdi.vapehubbeta.databinding.FragmentManagerEditBinding
import com.bozdi.vapehubbeta.databinding.FragmentOrderEditBinding
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
        return null
    }
}
//        val res = inflater.inflate(R.layout.fragment_manager_edit, container, false)
//
//        res.findViewById<TextView>(R.id.editManagerNameET).setText(selectManager.Name)
//        res.findViewById<TextView>(R.id.editManagerLoginET).setText(selectManager.Login)
//        res.findViewById<TextView>(R.id.editManagerStoreAddressET).setText("citie")
//        res.findViewById<TextView>(R.id.editManagerPhoneNumberET).setText(selectManager.Phone)

//        (getActivity()?.getApplicationContext() as AppServices).serverData.editManager(
//            selectManager.UserId.toString(),
//            res.findViewById<EditText>(R.id.newManagerPhoneNumberET).text.toString(),
//            res.findViewById<EditText>(R.id.newManagerLoginET).text.toString(),
//            res.findViewById<EditText>(R.id.newManagerPasswordET).text.toString(),
//            res.findViewById<EditText>(R.id.newManagerNameET).text.toString(),
//            StoresIds[spinnerStores.selectedItemPosition],
//
//            object : CreateOrderCallBack {
//                override fun onSuccess() {
//                    (getActivity()?.getApplicationContext() as AppServices).serverData.getCitiesList()
//                    activity?.supportFragmentManager?.beginTransaction()
//                        ?.replace(R.id.fragment_container, CitiesList())
//                        ?.addToBackStack(null)
//                        ?.commit()
//                }
//
//                override fun onError(text: String) {
//
//                    (getActivity()?.getApplicationContext() as AppServices).serverData.getCitiesList()
//                    activity?.supportFragmentManager?.beginTransaction()
//                        ?.replace(R.id.fragment_container, CitiesList())
//                        ?.addToBackStack(null)
//                        ?.commit()
//                }
//            }
////        )
//
//    }
//    return res
//
//}
//
//}