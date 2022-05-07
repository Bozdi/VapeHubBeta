package com.bozdi.vapehubbeta.managerFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import com.bozdi.vapehubbeta.*
import com.bozdi.vapehubbeta.databinding.FragmentCourierEditBinding
import com.bozdi.vapehubbeta.model.CouriersData

class CourierEdit(private var selectCourier: CouriersData,
                  private var CitiesIds: Array<String>,
                  private var  CitiesNames: Array<String>,
                  private var StoresIds: Array<String>,
                  private var  StoresNames: Array<String>,
) : Fragment() {
    private lateinit var binding: FragmentCourierEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCourierEditBinding.inflate(layoutInflater)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).supportActionBar?.title = getString(R.string.CourierEdit)
        val res = inflater.inflate(R.layout.fragment_courier_edit, container, false)

        val spinnerStores : Spinner = res.findViewById(R.id.spinnerStoreCourierEdit)

        val storesAdapter : ArrayAdapter<CharSequence> = ArrayAdapter(res.context, R.layout.spinner_item, StoresNames)
        storesAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinnerStores.adapter = storesAdapter

        res.findViewById<TextView>(R.id.editCourierNameET).text = selectCourier.Name
        res.findViewById<TextView>(R.id.editCourierLoginET).text = selectCourier.Login
        res.findViewById<TextView>(R.id.editCourierPhoneNumberET).text = selectCourier.Phone
        res.findViewById<TextView>(R.id.editCourierPasswordET).text = "1234"

        res.findViewById<Button>(R.id.editCourierSaveChangesButton).setOnClickListener {

            (activity?.applicationContext as AppServices).serverData.editUser(

                res.findViewById<EditText>(R.id.editCourierLoginET).text.toString(),
                res.findViewById<EditText>(R.id.editCourierPasswordET).text.toString(),
                res.findViewById<EditText>(R.id.editCourierNameET).text.toString(),
                res.findViewById<EditText>(R.id.editCourierPhoneNumberET).text.toString(),
                StoresIds[spinnerStores.selectedItemPosition],
                selectCourier.UserId,

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