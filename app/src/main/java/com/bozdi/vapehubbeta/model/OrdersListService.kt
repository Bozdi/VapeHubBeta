package com.bozdi.vapehubbeta.model



typealias ordersListener = (ordersData : List<OrdersData>) -> Unit

class OrderListService {
    private var orders = mutableListOf<OrdersData>()
    private val listeners = mutableSetOf<ordersListener>()

    fun getOrders(): List<OrdersData> {
        return orders;
    }

    fun add(order: OrdersData){
        orders.add(
            order
        )
    }

    fun del(order: OrdersData) {
        val indexToDel = orders.indexOfFirst { it.OrderId == order.OrderId }
        if (indexToDel != -1)
        {
            orders.removeAt(indexToDel)
            notifyChanges()
        }
    }

    fun addListener(listener: ordersListener) {
        listeners.add(listener)
        listener.invoke(orders)
    }

    fun removeListener(listener: ordersListener) {
        listeners.remove(listener)
    }

    private fun notifyChanges(){
        listeners.forEach{it.invoke(orders)}
    }

}