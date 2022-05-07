package com.bozdi.vapehubbeta.model



typealias ordersListener = (ordersData : List<OrdersData>) -> Unit

class OrderListService {
    private var orders = mutableListOf<OrdersData>()
    private val listeners = mutableSetOf<ordersListener>()

    fun getOrders(): List<OrdersData> {
        return orders;
    }

    fun add(order: OrdersData){
        val index = orders.indexOfFirst { it.OrderId == order.OrderId }
        if (index  == -1) {
            orders.add(
                order
            )
        } else {
            orders[index].StreetName = order.StreetName
            orders[index].ClientName = order.ClientName
            orders[index].BuildingNum = order.BuildingNum
            orders[index].ApartNum = order.ApartNum
            orders[index].EntranceNum = order.EntranceNum
            orders[index].ClientPhone = order.ClientPhone
            orders[index].Status = order.Status
        }
        //notifyChanges()
    }

    fun del(order: OrdersData) {
        val indexToDel = orders.indexOfFirst { it.OrderId == order.OrderId }
        if (indexToDel != -1) {
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
        listeners.forEach{
            it.invoke(orders)
        }
    }

}