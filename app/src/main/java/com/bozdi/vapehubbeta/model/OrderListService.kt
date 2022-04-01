package com.bozdi.vapehubbeta.model

import java.util.*

typealias ordersListener = (orders : List<Order>) -> Unit

class OrderListService {
    private var orders = mutableListOf<Order>()
    private val listeners = mutableSetOf<ordersListener>()

//    init {
//        orders.add(
//            Order(
//                "324",
//                "2",
//                "3",
//                "5"
//            )
//        )
//        orders.add(
//            Order(
//                "324",
//                "2",
//                "3",
//                "5"
//            )
//        )
//    }

    fun getOrders(): List<Order> {
        return orders;
    }

    fun addOrder(order: Order){
        orders.add(
            order
        )
    }

    fun delOrder(order: Order) {
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