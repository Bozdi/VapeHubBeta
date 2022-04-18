package com.bozdi.vapehubbeta.model

typealias selectedGoodsListener = (goodsDialogData : List<GoodsData>) -> Unit

class SelectedGoodsService {
    private var goods = mutableListOf<GoodsData>()
    private val listeners = mutableSetOf<selectedGoodsListener>()

    init {
    }

    fun getOrders(): List<GoodsData> {
        return goods;
    }

    fun add(order: GoodsData){
        val index = goods.indexOfFirst { it.GoodId == order.GoodId }
        if (index != -1)
        {
            goods[index].Available = (goods[index].Available?.toInt()?.plus(1)).toString()
        } else {
            order.Available = "1";
            goods.add(
                order
            )
        }
        notifyChanges()
    }

    fun del(order: GoodsData) {
        val indexToDel = goods.indexOfFirst { it.GoodId == order.GoodId }
        if (indexToDel != -1)
        {
            if ((goods[indexToDel].Available?.toInt()) != 1)
            {
                goods[indexToDel].Available = (goods[indexToDel].Available?.toInt()?.minus(1)).toString()

            } else{
                goods.removeAt(indexToDel)
            }
            notifyChanges()

        }
    }

    fun addListener(listener: selectedGoodsListener) {
        listeners.add(listener)
        listener.invoke(goods)
    }

    fun removeListener(listener: selectedGoodsListener) {
        listeners.remove(listener)
    }

    private fun notifyChanges(){
        listeners.forEach{it.invoke(goods)}
    }

}