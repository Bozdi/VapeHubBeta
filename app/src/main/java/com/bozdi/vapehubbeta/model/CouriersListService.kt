package com.bozdi.vapehubbeta.model



typealias couriersListener = (ordersData : List<CouriersData>) -> Unit

class CouriersListService {
    private var couriers = mutableListOf<CouriersData>()
    private val listeners = mutableSetOf<couriersListener>()

    fun getOrders(): List<CouriersData> {
        return couriers;
    }

    fun add(courier: CouriersData) {
        if (courier.Type != "COUR")
            return

        val index = couriers.indexOfFirst { it.UserId == courier.UserId }
        if (index  == -1) {
            couriers.add(
                courier
            )
        } else {
            couriers[index].Name = courier.Name
            couriers[index].StoreId = courier.StoreId
            couriers[index].Phone = courier.Phone
            couriers[index].Login = courier.Login
        }
       // notifyChanges()
    }

    fun del(courier: CouriersData) {
        val indexToDel = couriers.indexOfFirst { it.UserId == courier.UserId }
        if (indexToDel != -1) {
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
        listeners.forEach{
            it.invoke(couriers)}
    }

}