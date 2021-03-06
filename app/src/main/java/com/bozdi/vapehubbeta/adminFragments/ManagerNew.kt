package com.bozdi.vapehubbeta.adminFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.bozdi.vapehubbeta.*

class ManagerNew(private var CitiesIds: Array<String>,
                 private var  CitiesNames: Array<String>,
                 private var StoresIds: Array<String>,
                 private var  StoresNames: Array<String>,
) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container, ManagersList())
                    ?.addToBackStack(null)
                    ?.commit()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).supportActionBar?.title = getString(R.string.ManagerNew)
        val res = inflater.inflate(R.layout.fragment_manager_new, container, false)

        val spinnerCities : Spinner = res.findViewById(R.id.spinnerCities)
        val spinnerStores : Spinner = res.findViewById(R.id.spinnerStore)

        val citiesAdapter : ArrayAdapter<CharSequence> = ArrayAdapter(res.context, R.layout.spinner_item, CitiesNames)
        spinnerCities.adapter = citiesAdapter
        citiesAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        val storesAdapter : ArrayAdapter<CharSequence> = ArrayAdapter(res.context, R.layout.spinner_item, StoresNames)
        storesAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinnerStores.adapter = storesAdapter

        res.findViewById<Button>(R.id.createManagerButton).setOnClickListener {

            val userType = "MNGR"
            (activity?.applicationContext as AppServices).serverData.createManager(

                userType,
                res.findViewById<EditText>(R.id.newManagerPhoneNumberET).text.toString(),
                res.findViewById<EditText>(R.id.newManagerLoginET).text.toString(),
                res.findViewById<EditText>(R.id.newManagerPasswordET).text.toString(),
                res.findViewById<EditText>(R.id.newManagerNameET).text.toString(),
                StoresIds[spinnerStores.selectedItemPosition],

                object : CreateUserCallBack {
                    override fun onSuccess() {
                        (activity?.applicationContext as AppServices).serverData.getManagersList()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, ManagersList())
                            ?.addToBackStack(null)
                            ?.commit()
                    }

                    override fun onError(text: String) {
                        (activity?.applicationContext as AppServices).serverData.getManagersList()
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