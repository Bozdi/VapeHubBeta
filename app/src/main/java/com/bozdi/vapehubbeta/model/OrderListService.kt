package com.bozdi.vapehubbeta.model

import java.util.*
/*

Все Order менять на свой дата класс

 */

typealias ordersListener = (orders : List<Order>) -> Unit

class OrderListService {
    private var orders = mutableListOf<Order>()
    private val listeners = mutableSetOf<ordersListener>()

    fun getOrders(): List<Order> {
        return orders;
    }

    fun add(order: Order){
        orders.add(
            order
        )
    }

    fun del(order: Order) {
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