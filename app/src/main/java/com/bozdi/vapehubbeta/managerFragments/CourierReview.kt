package com.bozdi.vapehubbeta.managerFragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bozdi.vapehubbeta.*
import com.bozdi.vapehubbeta.model.CouriersData


class ManagerCourierReview(private var selectCourier: CouriersData, private var Street: String) : Fragment() {

    private val mHandler: Handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val res = inflater.inflate(R.layout.fragment_manager_courier_review, container, false)


        res.findViewById<TextView>(R.id.courierReviewNameET).text = selectCourier.Name
        res.findViewById<TextView>(R.id.courierReviewLoginET).text = selectCourier.Login
        res.findViewById<TextView>(R.id.courierReviewPhoneNumberET).text = selectCourier.Phone
        res.findViewById<TextView>(R.id.courierReviewStoreAddressET).text = Street

        res.findViewById<Button>(R.id.courierReviewEditButton).setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, CourierEdit(selectCourier))
                ?.addToBackStack(null)
                ?.commit()
        }

        res.findViewById<Button>(R.id.deleteCourierButton).setOnClickListener {

            (getActivity()?.getApplicationContext() as AppServices).serverData.deleteUser(

                selectCourier.UserId,

                object : CreateOrderCallBack {
                    override fun onSuccess() {
                        (getActivity()?.getApplicationContext() as AppServices).couriersService.del(selectCourier)
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

    private fun settext(Street: String)
    {
        activity?.findViewById<TextView>(R.id.courierReviewStoreAddressET)?.text =Street;
    }
}