package com.bozdi.vapehubbeta.managerFragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bozdi.vapehubbeta.*
import com.bozdi.vapehubbeta.adapters.*
import com.bozdi.vapehubbeta.model.*

class OrderNew() : Fragment() {
    private lateinit var newOrderAdapter: NewOrderAdapter
    private lateinit var selectedOrderAdapter: SelectedGoodsAdapter


    private val goodDialogService: GoodsDialogService
        get() = (getActivity()?.getApplicationContext() as AppServices).goodsDialogService

    private val selectedGoodsService: SelectedGoodsService
        get() = (getActivity()?.getApplicationContext() as AppServices).selectedGoodsService

    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dialog = context?.let { Dialog(it) }
        dialog?.setTitle("Dialog")
        dialog?.setContentView(R.layout.fragment_goods_list_dialog)
        val rv: RecyclerView? = dialog?.findViewById<RecyclerView>(R.id.Dialog_Goods_List)
        newOrderAdapter = NewOrderAdapter(object : NewOrderActionListener {
            override fun onGoodClick(good: GoodsData) {
                (getActivity()?.getApplicationContext() as AppServices).selectedGoodsService.add(good)
                (getActivity()?.getApplicationContext() as AppServices).goodsDialogService.del(good)
                dialog?.cancel()
            }
        })
        rv?.adapter = newOrderAdapter
        rv?.layoutManager = LinearLayoutManager(activity)
        goodDialogService.addListener {
            newOrderAdapter.goodsData = it
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as MainActivity).supportActionBar?.title = getString(R.string.OrderNew)
        val res = inflater.inflate(R.layout.fragment_order_new, container, false)

        val rv: RecyclerView = res.findViewById(R.id.selectedGoodList)
        selectedOrderAdapter = SelectedGoodsAdapter(object : SelectedGoodsActionListener {
            override fun onPlusClick(good: GoodsData) {
                (getActivity()?.getApplicationContext() as AppServices).selectedGoodsService.add(good)
            }

            override fun onMinusClick(good: GoodsData) {
                if (good.Available?.toInt() == 1) {
                    (getActivity()?.getApplicationContext() as AppServices).goodsDialogService.add(good)
                }
                (getActivity()?.getApplicationContext() as AppServices).selectedGoodsService.del(
                    good
                )
            }

        })
        rv.adapter = selectedOrderAdapter
        rv.layoutManager = LinearLayoutManager(activity)
        selectedGoodsService.addListener(selectedLister)

        res.findViewById<Button>(R.id.addGoodButton).setOnClickListener {
            if ((getActivity()?.getApplicationContext() as AppServices).goodsDialogService.count() > 0) {
                dialog?.show();
            } else{
                Toast.makeText(activity,"Нет товаров",Toast.LENGTH_SHORT).show()
            }
        }

        res.findViewById<Button>(R.id.createOrderButton).setOnClickListener {
            var apartNum = res.findViewById<EditText>(R.id.newOrderApartNumET).text.toString()

            var apartNumInt = 0
            if (apartNum != "") {
                apartNumInt = apartNum.toInt()
            }
            (getActivity()?.getApplicationContext() as AppServices).serverData.createOrder(
                GlobalVars.StoreId,
                res.findViewById<EditText>(R.id.newOrderStreetNameET).text.toString(),
                res.findViewById<EditText>(R.id.newOrderBuildingNumberET).text.toString(),
                apartNumInt,
                (getActivity()?.getApplicationContext() as AppServices).selectedGoodsService.getOrders(),
                res.findViewById<EditText>(R.id.newOrderNameET).text.toString(),
                res.findViewById<EditText>(R.id.newOrderPhoneNumberET).text.toString(),
                res.findViewById<EditText>(R.id.newOrderEntranceNumET).text.toString(),
                0,
                object : CreateOrderCallBack {
                    override fun onSuccess() {
                        (getActivity()?.getApplicationContext() as AppServices).serverData.getOrdersList()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, OrdersList())
                            ?.addToBackStack(null)
                            ?.commit()
                    }

                    override fun onError(text: String) {
                    }
                }
            )

        }
        return res
    }

    override fun onDestroy() {
        super.onDestroy()
        selectedGoodsService.removeListener (selectedLister)
    }
    private val selectedLister: selectedGoodsListener ={
        selectedOrderAdapter.goodsData = it
    }


}