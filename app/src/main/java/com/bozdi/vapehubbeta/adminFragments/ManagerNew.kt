package com.bozdi.vapehubbeta.adminFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.bozdi.vapehubbeta.AppServices
import com.bozdi.vapehubbeta.CreateOrderCallBack
import com.bozdi.vapehubbeta.R

class ManagerNew(private var CitiesIds: Array<String>,
                 private var  CitiesNames: Array<String>,
                 private var StoresIds: Array<String>,
                 private var  StoresNames: Array<String>,
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val res = inflater.inflate(R.layout.fragment_manager_new, container, false)

        var spinnerCities : Spinner = res.findViewById<Spinner>(R.id.spinnerCities);
        var spinnerStores : Spinner = res.findViewById<Spinner>(R.id.spinnerStore);


        var CitiesAdapter : ArrayAdapter<CharSequence> = ArrayAdapter(res.context, android.R.layout.simple_spinner_item, CitiesNames)
        spinnerCities.adapter = CitiesAdapter;

        var StoresAdapter : ArrayAdapter<CharSequence> = ArrayAdapter(res.context,
            android.R.layout.simple_spinner_item, StoresNames)
        spinnerStores.adapter = StoresAdapter;


       // spinnerCities.set
        res.findViewById<Button>(R.id.createManagerButton).setOnClickListener {
           // Toast.makeText(res.context,StoresIds[spinnerStores.selectedItemPosition],Toast.LENGTH_SHORT).show()

            val userType = "MNGR"
            (getActivity()?.getApplicationContext() as AppServices).serverData.createManager(
                userType,
                res.findViewById<EditText>(R.id.newManagerPhoneNumberET).text.toString(),
                res.findViewById<EditText>(R.id.newManagerLoginET).text.toString(),
                res.findViewById<EditText>(R.id.newManagerPasswordET).text.toString(),
                res.findViewById<EditText>(R.id.newManagerNameET).text.toString(),
                StoresIds[spinnerStores.selectedItemPosition],

                object : CreateOrderCallBack {
                    override fun onSuccess() {
                        (getActivity()?.getApplicationContext() as AppServices).serverData.getManagersList()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, ManagersList())
                            ?.addToBackStack(null)
                            ?.commit()
                        //Toast.makeText(res.context,"Товар успешно добавлен",Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(text: String) {
                        (getActivity()?.getApplicationContext() as AppServices).serverData.getManagersList()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, ManagersList())
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