package com.bozdi.vapehubbeta.model

typealias storesListener = (storeData : List<StoresData>) -> Unit

class StoresListService {
    private var stores = mutableListOf<StoresData>()
    private val listeners = mutableSetOf<storesListener>()
  //  public var selectedManagersStreet = "0"

    fun getStores(): List<StoresData> {
        return stores
    }

  //  fun test(street: SelectedManagersStoreData) {
   //     selectedManagersStreet = street.Street.toString()

   // }

    fun add(store: StoresData){
        val index = stores.indexOfFirst { it.StoreId == store.StoreId }
        if (index  == -1) {
            stores.add(
                store
            )
            //notifyChanges()
        } else {
            stores[index].Street = store.Street;
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

    fun getCitiesNameForCitiesId(id: String?): String {
        val index = stores.indexOfFirst { it.StoreId == id }
        if (index != -1 )
            return "${stores[index].Street} ${stores[index].BuildingNumber}"
        return "nichego"
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