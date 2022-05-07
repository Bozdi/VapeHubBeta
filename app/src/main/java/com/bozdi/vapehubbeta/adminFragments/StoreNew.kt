package com.bozdi.vapehubbeta.adminFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.OnBackPressedCallback
import com.bozdi.vapehubbeta.*

class StoreNew(private var CitiesNames: Array<String>,
               private var CitiesIds: Array<String>) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container, StoresList())
                    ?.addToBackStack(null)
                    ?.commit()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).supportActionBar?.title = getString(R.string.StoreNew)
        val res = inflater.inflate(R.layout.fragment_store_new, container, false)

        val spinnerCitiesNewStore : Spinner = res.findViewById(R.id.newStoreCitiesSpinner)
        val citiesAdapter : ArrayAdapter<CharSequence> = ArrayAdapter(res.context, R.layout.spinner_item, CitiesNames)
        spinnerCitiesNewStore.adapter = citiesAdapter
        citiesAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        res.findViewById<Button>(R.id.createStoreButton).setOnClickListener {

            (activity?.applicationContext as AppServices).serverData.createStore(

                CitiesIds[spinnerCitiesNewStore.selectedItemPosition].toInt(),
                res.findViewById<EditText>(R.id.newStoreStreetET).text.toString(),
                res.findViewById<EditText>(R.id.newStoreBuildingNumber).text.toString(),

                object : CreateOrderCallBack {
                    override fun onSuccess() {
                        (activity?.applicationContext as AppServices).serverData.getStoresList()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, StoresList())
                            ?.addToBackStack(null)
                            ?.commit()
                    }

                    override fun onError(text: String) {

                        (activity?.applicationContext as AppServices).serverData.getStoresList()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, StoresList())
                            ?.addToBackStack(null)
                            ?.commit()
                    }
                }
            )

        }

        return res
    }
}