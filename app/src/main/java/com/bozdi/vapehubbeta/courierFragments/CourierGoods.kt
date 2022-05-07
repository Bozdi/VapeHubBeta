package com.bozdi.vapehubbeta.courierFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bozdi.vapehubbeta.AppServices
import com.bozdi.vapehubbeta.MainActivity
import com.bozdi.vapehubbeta.R
import com.bozdi.vapehubbeta.adapters.CourierGoodsAdapter
import com.bozdi.vapehubbeta.model.CourierGoodsListService
import com.bozdi.vapehubbeta.model.courierGoodsListener


class CourierGoods : Fragment() {
    private lateinit var adapter: CourierGoodsAdapter

    private val courierService: CourierGoodsListService
        get() = (activity?.applicationContext as AppServices).courierGoodsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        (activity as MainActivity).supportActionBar?.title = getString(R.string.Goods)
        val res = inflater.inflate(R.layout.fragment_courier_backpack, container, false)
        val rv: RecyclerView = res.findViewById(R.id.Goods_List)
        adapter = CourierGoodsAdapter()
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(activity)
        courierService.addListener(courierGoodsListener)
        return res
    }

    override fun onDestroy() {
        super.onDestroy()
        courierService.removeListener(courierGoodsListener)
    }

    private val courierGoodsListener: courierGoodsListener ={
        adapter.goodsData = it
    }


}