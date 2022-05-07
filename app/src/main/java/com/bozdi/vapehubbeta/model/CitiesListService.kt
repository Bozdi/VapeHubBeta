package com.bozdi.vapehubbeta.model

typealias citiesListener = (orderData : List<CitiesData>) -> Unit

class CitiesListService {
    private var cities = mutableListOf<CitiesData>()
    private val listeners = mutableSetOf<citiesListener>()

    fun getOrders(): List<CitiesData> {
        return cities
    }

    fun add(city: CitiesData){
        val index = cities.indexOfFirst { it.CityId == city.CityId }
        if (index  == -1){
            cities.add(
                city
            )
            //notifyChanges()
        } else {
            cities[index].Name = city.Name
        }
    }

    fun del(city: CitiesData) {
        val indexToDel = cities.indexOfFirst { it.CityId == city.CityId }
        if (indexToDel != -1 && indexToDel != 3) {
            cities.removeAt(indexToDel)
            notifyChanges()
        }
    }

    fun addListener(listener: citiesListener) {
        listeners.add(listener)
        listener.invoke(cities)
    }

    fun removeListener(listener: citiesListener) {
        listeners.remove(listener)
    }

    private fun notifyChanges(){
        listeners.forEach{
            it.invoke(cities)}
    }

}