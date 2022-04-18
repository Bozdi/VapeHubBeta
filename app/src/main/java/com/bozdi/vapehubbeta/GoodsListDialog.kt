package com.bozdi.vapehubbeta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bozdi.vapehubbeta.adapters.NewOrderAdapter
import com.bozdi.vapehubbeta.model.GoodsDialogService
import com.bozdi.vapehubbeta.model.goodsDialogListener

class GoodsListDialog : Fragment() {
    private lateinit var adapter: NewOrderAdapter

    private val goodDialogService: GoodsDialogService
        get() = (getActivity()?.getApplicationContext() as AppServices).goodsDialogService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val res = inflater.inflate(R.layout.fragment_goods_list_dialog, container, false)
//        var rv: RecyclerView = res.findViewById(R.id.Dialog_Goods_List)
//        adapter = NewOrderAdapter()
//        rv.adapter = adapter
//        rv.layoutManager = LinearLayoutManager(activity)
//        goodDialogService.addListener(goodsAdapterListener)
        return res
    }

    override fun onDestroy() {
        super.onDestroy()
        goodDialogService.removeListener(goodsAdapterListener)
    }

    private val goodsAdapterListener: goodsDialogListener ={
        adapter.goodsData = it
    }


}