package com.bozdi.vapehubbeta.adminFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.bozdi.vapehubbeta.AppServices
import com.bozdi.vapehubbeta.CreateOrderCallBack
import com.bozdi.vapehubbeta.R
import com.bozdi.vapehubbeta.adapters.NewOrderAdapter
import com.bozdi.vapehubbeta.adapters.SelectedGoodsAdapter
import com.bozdi.vapehubbeta.model.SelectedGoodsService
import com.bozdi.vapehubbeta.model.selectedGoodsListener

class ManagerNew : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val res = inflater.inflate(R.layout.fragment_manager_new, container, false)

        res.findViewById<Button>(R.id.createManagerButton).setOnClickListener {

            val userType = "MNGR"
            (getActivity()?.getApplicationContext() as AppServices).serverData.createManager(

                userType,
                res.findViewById<EditText>(R.id.newManagerPhoneNumberET).text.toString(),
                res.findViewById<EditText>(R.id.newManagerLoginET).text.toString(),
                res.findViewById<EditText>(R.id.newManagerPasswordET).text.toString(),
                res.findViewById<EditText>(R.id.newManagerNameET).text.toString(),

                object : CreateOrderCallBack {
                    override fun onSuccess() {
                        (getActivity()?.getApplicationContext() as AppServices).serverData.getManagersList()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, ManagersList())
                            ?.addToBackStack(null)
                            ?.commit()
                        //Toast.makeText(activity,"Товар успешно добавлен",Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(text: String) {
                        (getActivity()?.getApplicationContext() as AppServices).serverData.getManagersList()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, ManagersList())
                            ?.addToBackStack(null)
                            ?.commit()
                        //Toast.makeText(activity,text,Toast.LENGTH_SHORT).show()
                    }
                }
            )

        }

        return res
    }


}