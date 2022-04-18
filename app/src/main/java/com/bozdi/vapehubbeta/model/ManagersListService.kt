package com.bozdi.vapehubbeta.model

typealias managersListener = (managers : List<ManagersData>) -> Unit

class ManagersListService {
    private var managers = mutableListOf<ManagersData>()
    private val listeners = mutableSetOf<managersListener>()

    fun getManagers(): List<ManagersData> {
        return managers;
    }

    fun add(manager: ManagersData){
        managers.add(
            manager
        )
        //notifyChanges()
    }

    fun del(order: ManagersData) {
        val indexToDel = managers.indexOfFirst { it.UserId == order.UserId }
        if (indexToDel != -1)
        {
            managers.removeAt(indexToDel)
            notifyChanges()
        }
    }

    fun addListener(listener: managersListener) {
        listeners.add(listener)
        listener.invoke(managers)
    }

    fun removeListener(listener: managersListener) {
        listeners.remove(listener)
    }

    private fun notifyChanges(){
        listeners.forEach{it.invoke(managers)}
    }

}