package com.bozdi.vapehubbeta.adminFragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.bozdi.vapehubbeta.AppServices
import com.bozdi.vapehubbeta.CreateOrderCallBack
import com.bozdi.vapehubbeta.GlobalVars
import com.bozdi.vapehubbeta.R
import com.bozdi.vapehubbeta.databinding.FragmentCityEditBinding
import com.bozdi.vapehubbeta.databinding.FragmentOrderEditBinding
import com.bozdi.vapehubbeta.model.CitiesData
import com.bozdi.vapehubbeta.model.OrdersData
import java.util.*

class CityEdit(private var selectCity: CitiesData) : Fragment() {
    private lateinit var binding: FragmentCityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCityEditBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val res = inflater.inflate(R.layout.fragment_city_edit, container, false)

        res.findViewById<TextView>(R.id.editCityNameET).setText(selectCity.Name)
        res.findViewById<Button>(R.id.editCitySaveChangesButton).setOnClickListener {

            (getActivity()?.getApplicationContext() as AppServices).serverData.editCity(
                selectCity.CityId.toString(),
                res.findViewById<EditText>(R.id.editCityNameET).text.toString(),

                object : CreateOrderCallBack {
                    override fun onSuccess() {
                        (getActivity()?.getApplicationContext() as AppServices).serverData.getCitiesList()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, CitiesList())
                            ?.addToBackStack(null)
                            ?.commit()
                    }

                    override fun onError(text: String) {

                        (getActivity()?.getApplicationContext() as AppServices).serverData.getCitiesList()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, CitiesList())
                            ?.addToBackStack(null)
                            ?.commit()
                    }
                }
            )

        }
        return res

    }

}