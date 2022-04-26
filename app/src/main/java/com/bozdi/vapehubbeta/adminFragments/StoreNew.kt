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
import com.bozdi.vapehubbeta.GlobalVars
import com.bozdi.vapehubbeta.R

class StoreNew : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val res = inflater.inflate(R.layout.fragment_store_new, container, false)

        res.findViewById<Button>(R.id.createStoreButton).setOnClickListener {

            (getActivity()?.getApplicationContext() as AppServices).serverData.createStore(

                res.findViewById<EditText>(R.id.newStoreStreetET).text.toString(),
                GlobalVars.CityId,
                res.findViewById<EditText>(R.id.newStoreBuildingNumber).text.toString(),

                object : CreateOrderCallBack {
                    override fun onSuccess() {
                        (getActivity()?.getApplicationContext() as AppServices).serverData.getStoresList()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, StoresList())
                            ?.addToBackStack(null)
                            ?.commit()
                        //Toast.makeText(activity,"Товар успешно добавлен",Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(text: String) {

                        (getActivity()?.getApplicationContext() as AppServices).serverData.getStoresList()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, StoresList())
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