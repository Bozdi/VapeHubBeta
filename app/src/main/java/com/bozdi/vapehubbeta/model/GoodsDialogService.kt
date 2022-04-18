package com.bozdi.vapehubbeta.model

typealias goodsDialogListener = (goodsDialogData : List<GoodsData>) -> Unit

class GoodsDialogService {
    private var goods = mutableListOf<GoodsData>()
    private val listeners = mutableSetOf<goodsDialogListener>()

    fun getOrders(): List<GoodsData> {
        return goods;
    }

    fun add(order: GoodsData){
        goods.add(
            order
        )
        notifyChanges()
    }

    fun del(order: GoodsData) {
        val indexToDel = goods.indexOfFirst { it.GoodId == order.GoodId }
        if (indexToDel != -1)
        {
            goods.removeAt(indexToDel)
            notifyChanges()
        }
    }

    fun count(): Int {
       return goods.count();
    }

    fun addListener(listener: goodsDialogListener) {
        listeners.add(listener)
        listener.invoke(goods)
    }

    fun removeListener(listener: goodsDialogListener) {
        listeners.remove(listener)
    }

    private fun notifyChanges(){
        listeners.forEach{it.invoke(goods)}
    }

}