package com.bozdi.vapehubbeta.managerFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.bozdi.vapehubbeta.*
import com.bozdi.vapehubbeta.databinding.FragmentCourierEditBinding
import com.bozdi.vapehubbeta.model.CouriersData

class CourierEdit(private var selectCourier: CouriersData) : Fragment() {
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

        res.findViewById<TextView>(R.id.editCourierNameET).setText(selectCourier.Name)
        res.findViewById<TextView>(R.id.editCourierLoginET).setText(selectCourier.Login)
        res.findViewById<TextView>(R.id.editCourierPhoneNumberET).setText(selectCourier.Phone)

        res.findViewById<Button>(R.id.editCourierSaveChangesButton).setOnClickListener {

            (getActivity()?.getApplicationContext() as AppServices).serverData.editUser(

                res.findViewById<EditText>(R.id.editCourierLoginET).text.toString(),
                res.findViewById<EditText>(R.id.editCourierPasswordET).text.toString(),
                res.findViewById<EditText>(R.id.editCourierNameET).text.toString(),
                res.findViewById<EditText>(R.id.editCourierPhoneNumberET).text.toString(),
                "3",
                selectCourier.UserId,

                object : CreateUserCallBack {
                    override fun onSuccess() {
                        (getActivity()?.getApplicationContext() as AppServices).serverData.getCouriersList()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, CouriersList())
                            ?.addToBackStack(null)
                            ?.commit()
                    }

                    override fun onError(text: String) {
                        (getActivity()?.getApplicationContext() as AppServices).serverData.getCouriersList()
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