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
import com.bozdi.vapehubbeta.AppServices
import com.bozdi.vapehubbeta.CreateOrderCallBack
import com.bozdi.vapehubbeta.R
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

        val res = inflater.inflate(R.layout.fragment_courier_new, container, false)

        val spinnerCities : Spinner = res.findViewById<Spinner>(R.id.spinnerCitiesCourier)
        val spinnerStores : Spinner = res.findViewById<Spinner>(R.id.spinnerStoreCourier)

        val citiesAdapter : ArrayAdapter<CharSequence> = ArrayAdapter(res.context, R.layout.spinner_item, CitiesNames)
        spinnerCities.adapter = citiesAdapter
        citiesAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        val storesAdapter : ArrayAdapter<CharSequence> = ArrayAdapter(res.context, R.layout.spinner_item, StoresNames)
        storesAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinnerStores.adapter = storesAdapter

        res.findViewById<Button>(R.id.createCourierButton).setOnClickListener {

            val userType = "COUR"
            (getActivity()?.getApplicationContext() as AppServices).serverData.createCourier(

                userType,
                res.findViewById<EditText>(R.id.newManagerPhoneNumberET).text.toString(),
                res.findViewById<EditText>(R.id.newManagerLoginET).text.toString(),
                res.findViewById<EditText>(R.id.newManagerPasswordET).text.toString(),
                res.findViewById<EditText>(R.id.newManagerNameET).text.toString(),
                StoresIds[spinnerStores.selectedItemPosition],

                object : CreateOrderCallBack {
                    override fun onSuccess() {
                        (getActivity()?.getApplicationContext() as AppServices).serverData.getCouriersList()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, CouriersList())
                            ?.addToBackStack(null)
                            ?.commit()
                    }

                    override fun onError(text: String) {
                        (getActivity()?.getApplicationContext() as AppServices).serverData.getCouriersList()
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