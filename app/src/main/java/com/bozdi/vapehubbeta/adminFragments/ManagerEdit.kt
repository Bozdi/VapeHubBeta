package com.bozdi.vapehubbeta.adminFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bozdi.vapehubbeta.*
import com.bozdi.vapehubbeta.databinding.FragmentManagerEditBinding
import com.bozdi.vapehubbeta.model.ManagersData

class ManagerEdit(private var CitiesIds: Array<String>,
                  private var  CitiesNames: Array<String>,
                  private var StoresIds: Array<String>,
                  private var  StoresNames: Array<String>,
                  private var selectManager: ManagersData,
) : Fragment() {
    private lateinit var binding: FragmentManagerEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentManagerEditBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).supportActionBar?.title = getString(R.string.ManagerEdit)
        val res = inflater.inflate(R.layout.fragment_manager_edit, container, false)

        //val spinnerCitiesManagerEdit : Spinner = res.findViewById(R.id.spinnerCities)
        val spinnerStoresManagerEdit : Spinner = res.findViewById(R.id.spinnerStoreManagerEdit)


        val storesAdapter : ArrayAdapter<CharSequence> = ArrayAdapter(res.context, R.layout.spinner_item, StoresNames)
        storesAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinnerStoresManagerEdit.adapter = storesAdapter

        res.findViewById<TextView>(R.id.editManagerNameET).setText(selectManager.Name)
        res.findViewById<TextView>(R.id.editManagerLoginET).setText(selectManager.Login)
        res.findViewById<TextView>(R.id.editManagerPhoneNumberET).setText(selectManager.Phone)

        res.findViewById<Button>(R.id.editManagerSaveChangesButton).setOnClickListener {

            (getActivity()?.getApplicationContext() as AppServices).serverData.editUser(

                res.findViewById<EditText>(R.id.editManagerLoginET).text.toString(),
                res.findViewById<EditText>(R.id.editManagerPasswordET).text.toString(),
                res.findViewById<EditText>(R.id.editManagerNameET).text.toString(),
                res.findViewById<EditText>(R.id.editManagerPhoneNumberET).text.toString(),
                StoresIds[spinnerStoresManagerEdit.selectedItemPosition],
                selectManager.UserId,

                object : CreateUserCallBack {
                    override fun onSuccess() {
                        (getActivity()?.getApplicationContext() as AppServices).serverData.getManagersList()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, ManagersList())
                            ?.addToBackStack(null)
                            ?.commit()
                    }

                    override fun onError(text: String) {
                        (getActivity()?.getApplicationContext() as AppServices).serverData.getManagersList()
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