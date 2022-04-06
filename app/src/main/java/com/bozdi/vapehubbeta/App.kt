package com.bozdi.vapehubbeta

import android.app.Application
import com.bozdi.vapehubbeta.managerFragments.CouriersList
import com.bozdi.vapehubbeta.model.CouriersListService
import com.bozdi.vapehubbeta.model.OrderListService

class App : Application(){
    val ordersService = OrderListService()
    val couriersService = CouriersListService() //OrderListService аменить на  couriersListService
   // val ordersService = OrderListService()//создать объект под курьеров с соответствующим названием
}