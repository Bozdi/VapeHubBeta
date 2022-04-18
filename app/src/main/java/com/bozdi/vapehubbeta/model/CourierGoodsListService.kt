package com.bozdi.vapehubbeta.model

typealias courierGoodsListener = (orderData : List<CourierGoodsData>) -> Unit

class CourierGoodsListService {
    private var goods = mutableListOf<CourierGoodsData>()
    private val listeners = mutableSetOf<courierGoodsListener>()

    fun getOrders(): List<CourierGoodsData> {
        return goods;
    }

    fun add(order: CourierGoodsData){
        goods.add(
            order
        )
        //notifyChanges()
    }

    fun del(order: CourierGoodsData) {
        val indexToDel = goods.indexOfFirst { it.GoodId == order.GoodId }
        if (indexToDel != -1)
        {
            goods.removeAt(indexToDel)
            notifyChanges()
        }
    }

    fun addListener(listener: courierGoodsListener) {
        listeners.add(listener)
        listener.invoke(goods)
    }

    fun removeListener(listener: courierGoodsListener) {
        listeners.remove(listener)
    }

    private fun notifyChanges(){
        listeners.forEach{it.invoke(goods)}
    }

}