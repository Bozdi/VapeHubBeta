package com.bozdi.vapehubbeta.adminFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import com.bozdi.vapehubbeta.*
import com.bozdi.vapehubbeta.databinding.FragmentStoreEditBinding

import com.bozdi.vapehubbeta.model.StoresData

class StoreEdit(private var selectStore: StoresData,
                private var CitiesNames: Array<String>,
                private var CitiesIds: Array<String>) : Fragment() {

    private lateinit var binding: FragmentStoreEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentStoreEditBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).supportActionBar?.title = getString(R.string.StoreEdit)
        val res = inflater.inflate(R.layout.fragment_store_edit, container, false)

        val spinnerCitiesEditStore : Spinner = res.findViewById(R.id.editStoreCitiesSpinner)
        val citiesAdapter : ArrayAdapter<CharSequence> = ArrayAdapter(res.context, R.layout.spinner_item, CitiesNames)
        spinnerCitiesEditStore.adapter = citiesAdapter
        citiesAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        res.findViewById<TextView>(R.id.editStoreStreetET).text = selectStore.Street
        res.findViewById<TextView>(R.id.editStoreBuildingNumberET).text = selectStore.BuildingNumber

        res.findViewById<Button>(R.id.editStoreSaveChangesButton).setOnClickListener {

            (activity?.applicationContext as AppServices).serverData.editStore(

                selectStore.StoreId,
                CitiesIds[spinnerCitiesEditStore.selectedItemPosition].toInt(),
                res.findViewById<EditText>(R.id.editStoreStreetET).text.toString(),
                res.findViewById<EditText>(R.id.editStoreBuildingNumberET).text.toString(),

                object : EditCityCallback {
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