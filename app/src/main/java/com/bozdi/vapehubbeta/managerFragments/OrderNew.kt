package com.bozdi.vapehubbeta.managerFragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bozdi.vapehubbeta.AppServices
import com.bozdi.vapehubbeta.GoodsListDialog
import com.bozdi.vapehubbeta.R
import com.bozdi.vapehubbeta.adapters.*
import com.bozdi.vapehubbeta.model.*
import com.google.android.material.floatingactionbutton.FloatingActionButton

class OrderNew : Fragment() {
    private lateinit var NewOrderadapter: NewOrderAdapter
    private lateinit var selectedOrderadapter: SelectedGoodsAdapter


    private val goodDialogService: GoodsDialogService
        get() = (getActivity()?.getApplicationContext() as AppServices).goodsDialogService

    private val selectedGoodsService: SelectedGoodsService
        get() = (getActivity()?.getApplicationContext() as AppServices).selectedGoodsService

    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dialog = context?.let { Dialog(it) }
        dialog?.setTitle("Пидр")
        dialog?.setContentView(R.layout.fragment_goods_list_dialog)
        val rv: RecyclerView? = dialog?.findViewById<RecyclerView>(R.id.Dialog_Goods_List)
        NewOrderadapter = NewOrderAdapter(object : NewOrderActionListener {
            override fun onGoodClick(good: GoodsData) {
                (getActivity()?.getApplicationContext() as AppServices).selectedGoodsService.add(good)
                (getActivity()?.getApplicationContext() as AppServices).goodsDialogService.del(good)
                dialog?.cancel();
            }
        })
        rv?.adapter = NewOrderadapter
        rv?.layoutManager = LinearLayoutManager(activity)
        goodDialogService.addListener {
            NewOrderadapter.goodsData = it
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val res = inflater.inflate(R.layout.fragment_order_new, container, false)

        val rv: RecyclerView = res.findViewById(R.id.selectedGoodList)
        selectedOrderadapter = SelectedGoodsAdapter(object : SelectedGoodsActionListener {
            override fun onPlusArturPidorClick(good: GoodsData) {
                (getActivity()?.getApplicationContext() as AppServices).selectedGoodsService.add(good)
            }

            override fun onMinusArturSinSobakyClick(good: GoodsData) {
                if (good.Available?.toInt() == 1) {
                    (getActivity()?.getApplicationContext() as AppServices).goodsDialogService.add(good)
                }
                (getActivity()?.getApplicationContext() as AppServices).selectedGoodsService.del(
                    good
                )
            }

        })
        rv.adapter = selectedOrderadapter
        rv.layoutManager = LinearLayoutManager(activity)
        selectedGoodsService.addListener(selectedLister)

        res.findViewById<Button>(R.id.addGoodButton).setOnClickListener {
            if ((getActivity()?.getApplicationContext() as AppServices).goodsDialogService.count() > 0) {
                dialog?.show();
            } else{
                Toast.makeText(activity,"Нет товаров, ты Пидр",Toast.LENGTH_SHORT).show()
            }
        }
        return res
    }

    override fun onDestroy() {
        super.onDestroy()
        selectedGoodsService.removeListener (selectedLister)
    }
    private val selectedLister: selectedGoodsListener ={
        selectedOrderadapter.goodsData = it
    }


}