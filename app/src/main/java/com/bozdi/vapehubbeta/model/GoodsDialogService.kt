package com.bozdi.vapehubbeta.model

typealias goodsDialogListener = (goodsDialogData : List<GoodsDialogData>) -> Unit

class GoodsDialogService {
    private var goods = mutableListOf<GoodsDialogData>()
    private val listeners = mutableSetOf<goodsDialogListener>()

    fun getOrders(): List<GoodsDialogData> {
        return goods;
    }

    fun add(order: GoodsDialogData){
        goods.add(
            order
        )
        //notifyChanges()
    }

    fun del(order: GoodsDialogData) {
        val indexToDel = goods.indexOfFirst { it.GoodId == order.GoodId }
        if (indexToDel != -1)
        {
            goods.removeAt(indexToDel)
            notifyChanges()
        }
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