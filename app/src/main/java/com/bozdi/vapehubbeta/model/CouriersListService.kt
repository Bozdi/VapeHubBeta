package com.bozdi.vapehubbeta.model



typealias couriersListener = (orders : List<CouriersData>) -> Unit

class CouriersListService {
    private var couriers = mutableListOf<CouriersData>()
    private val listeners = mutableSetOf<couriersListener>()

    fun getOrders(): List<CouriersData> {
        return couriers;
    }

    fun add(order: CouriersData){
        couriers.add(
            order
        )
    }

    fun del(order: CouriersData) {
        val indexToDel = couriers.indexOfFirst { it.Name == order.Name }
        if (indexToDel != -1)
        {
            couriers.removeAt(indexToDel)
            notifyChanges()
        }
    }

    fun addListener(listener: couriersListener) {
        listeners.add(listener)
        listener.invoke(couriers)
    }

    fun removeListener(listener: couriersListener) {
        listeners.remove(listener)
    }

    private fun notifyChanges(){
        listeners.forEach{it.invoke(couriers)}
    }

}