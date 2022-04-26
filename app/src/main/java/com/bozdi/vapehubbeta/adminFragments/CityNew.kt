package com.bozdi.vapehubbeta.adminFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.bozdi.vapehubbeta.*

class CityNew : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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
                    //Toast.makeText(activity,"Товар успешно добавлен",Toast.LENGTH_SHORT).show()
                }

                override fun onError(text: String) {
                    (getActivity()?.getApplicationContext() as AppServices).serverData.getCitiesList()
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.fragment_container, CitiesList())
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