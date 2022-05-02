package com.bozdi.vapehubbeta.adminFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.bozdi.vapehubbeta.AppServices
import com.bozdi.vapehubbeta.CreateOrderCallBack
import com.bozdi.vapehubbeta.R
import com.bozdi.vapehubbeta.managerFragments.OrderEdit
import com.bozdi.vapehubbeta.model.CitiesData
import com.bozdi.vapehubbeta.model.OrdersData
import java.util.jar.Attributes

class CityReview(private var selectCity: CitiesData) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val res = inflater.inflate(R.layout.fragment_city_review, container, false)

        res.findViewById<TextView>(R.id.cityReviewNameET).setText(selectCity.Name)


        res.findViewById<Button>(R.id.cityReviewEditButton).setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, CityEdit(selectCity))
                ?.addToBackStack(null)
                ?.commit()
        }

        res.findViewById<Button>(R.id.deleteCityButton).setOnClickListener {

            (getActivity()?.getApplicationContext() as AppServices).serverData.deleteCity(

                selectCity.CityId.toString(),

                object : CreateOrderCallBack {
                    override fun onSuccess() {
                        (getActivity()?.getApplicationContext() as AppServices).citiesService.del(selectCity)
                        (getActivity()?.getApplicationContext() as AppServices).serverData.getCitiesList()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, CitiesList())
                            ?.addToBackStack(null)
                            ?.commit()
                       }

                    override fun onError(text: String) {
                        (getActivity()?.getApplicationContext() as AppServices).citiesService.del(selectCity)
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