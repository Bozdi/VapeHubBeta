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
import androidx.activity.OnBackPressedCallback
import com.bozdi.vapehubbeta.AppServices
import com.bozdi.vapehubbeta.CreateOrderCallBack
import com.bozdi.vapehubbeta.MainActivity
import com.bozdi.vapehubbeta.R
import com.bozdi.vapehubbeta.managerFragments.OrderEdit
import com.bozdi.vapehubbeta.model.CitiesData
import com.bozdi.vapehubbeta.model.OrdersData
import java.util.jar.Attributes

class CityReview(private var selectCity: CitiesData) : Fragment() {

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
        val res = inflater.inflate(R.layout.fragment_city_review, container, false)

        (activity as MainActivity).supportActionBar?.title = selectCity.Name
        res.findViewById<TextView>(R.id.cityReviewNameET).text = selectCity.Name


        res.findViewById<Button>(R.id.cityReviewEditButton).setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, CityEdit(selectCity))
                ?.addToBackStack(null)
                ?.commit()
        }

        res.findViewById<Button>(R.id.deleteCityButton).setOnClickListener {

            if (selectCity.CityId != "3") {
                (activity?.applicationContext as AppServices).serverData.deleteCity(

                    selectCity.CityId.toString(),

                    object : CreateOrderCallBack {
                        override fun onSuccess() {
                            (activity?.applicationContext as AppServices).citiesService.del(
                                selectCity
                            )
                            (activity?.applicationContext as AppServices).serverData.getCitiesList()
                            activity?.supportFragmentManager?.beginTransaction()
                                ?.replace(R.id.fragment_container, CitiesList())
                                ?.addToBackStack(null)
                                ?.commit()
                        }

                        override fun onError(text: String) {
                            (activity?.applicationContext as AppServices).citiesService.del(
                                selectCity
                            )
                            (activity?.applicationContext as AppServices).serverData.getCitiesList()
                            activity?.supportFragmentManager?.beginTransaction()
                                ?.replace(R.id.fragment_container, CitiesList())
                                ?.addToBackStack(null)
                                ?.commit()

                        }
                    }
                )
            } else {
                Toast.makeText(activity,"Невозможно удалить данный город", Toast.LENGTH_SHORT).show()
            }
        }

        return res
    }

}