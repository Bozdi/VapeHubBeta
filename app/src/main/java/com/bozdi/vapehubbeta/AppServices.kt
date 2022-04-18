package com.bozdi.vapehubbeta

import android.app.Application
import com.bozdi.vapehubbeta.managerFragments.CouriersList
import com.bozdi.vapehubbeta.model.*

class AppServices : Application() {
    val ordersService = OrderListService()
    val couriersService = CouriersListService()
    val citiesService = CitiesListService()
    val managersService = ManagersListService()
    val storesService = StoresListService()
    val courierGoodsService = CourierGoodsListService()
    val goodsDialogService = GoodsDialogService()
    val serverData = ServerData(this)
}