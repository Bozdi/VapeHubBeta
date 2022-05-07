package com.bozdi.vapehubbeta.adminFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import com.bozdi.vapehubbeta.*

class CityNew : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container, CitiesList())
                    ?.addToBackStack(null)
                    ?.commit()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).supportActionBar?.title = getString(R.string.CityNew)

        val res = inflater.inflate(R.layout.fragment_city_new, container, false)

        res.findViewById<Button>(R.id.createCityButton).setOnClickListener {

        (getActivity()?.getApplicationContext() as AppServices).serverData.createCity(

            res.findViewById<EditText>(R.id.newCityNameET).text.toString(),

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