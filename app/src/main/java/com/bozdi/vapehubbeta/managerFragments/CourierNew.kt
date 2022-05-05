package com.bozdi.vapehubbeta.managerFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.bozdi.vapehubbeta.*
import com.bozdi.vapehubbeta.adminFragments.CitiesList

class CourierNew(private var CitiesIds: Array<String>,
                 private var  CitiesNames: Array<String>,
                 private var StoresIds: Array<String>,
                 private var  StoresNames: Array<String>,
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).supportActionBar?.title = getString(R.string.CourierNew)
        val res = inflater.inflate(R.layout.fragment_courier_new, container, false)

        val spinnerStores : Spinner = res.findViewById(R.id.spinnerStoreCourier)

        val storesAdapter : ArrayAdapter<CharSequence> = ArrayAdapter(res.context, R.layout.spinner_item, StoresNames)
        storesAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinnerStores.adapter = storesAdapter

        res.findViewById<Button>(R.id.createCourierButton).setOnClickListener {

            val userType = "COUR"
            (activity?.applicationContext as AppServices).serverData.createCourier(

                userType,
                res.findViewById<EditText>(R.id.newCourierPhoneNumberET).text.toString(),
                res.findViewById<EditText>(R.id.newCourierLoginET).text.toString(),
                res.findViewById<EditText>(R.id.newCourierPasswordET).text.toString(),
                res.findViewById<EditText>(R.id.newCourierNameET).text.toString(),
                StoresIds[spinnerStores.selectedItemPosition],

                object : CreateUserCallBack {
                    override fun onSuccess() {
                        (activity?.applicationContext as AppServices).serverData.getCouriersList()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, CouriersList())
                            ?.addToBackStack(null)
                            ?.commit()
                    }

                    override fun onError(text: String) {
                        (activity?.applicationContext as AppServices).serverData.getCouriersList()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, CouriersList())
                            ?.addToBackStack(null)
                            ?.commit()
                    }
                }
            )

        }

        return res
    }
}