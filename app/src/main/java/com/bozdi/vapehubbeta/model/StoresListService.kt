package com.bozdi.vapehubbeta.model

typealias storesListener = (storeData : List<StoresData>) -> Unit

class StoresListService {
    private var stores = mutableListOf<StoresData>()
    private val listeners = mutableSetOf<storesListener>()

    fun getStores(): List<StoresData> {
        return stores
    }


    fun add(store: StoresData){
        val index = stores.indexOfFirst { it.StoreId == store.StoreId }
        if (index  == -1) {
            stores.add(
                store
            )
            //notifyChanges()
        } else {
            stores[index].CityId = store.CityId
            stores[index].Street = store.Street
            stores[index].BuildingNumber = store.BuildingNumber
        }
    }

    fun del(store: StoresData) {
        val indexToDel = stores.indexOfFirst { it.StoreId == store.StoreId }
        if (indexToDel != -1)
        {
            stores.removeAt(indexToDel)
            notifyChanges()
        }
    }

    fun addListener(listener: storesListener) {
        listeners.add(listener)
        listener.invoke(stores)
    }

    fun removeListener(listener: storesListener) {
        listeners.remove(listener)
    }

    private fun notifyChanges(){
        listeners.forEach{it.invoke(stores)}
    }

}