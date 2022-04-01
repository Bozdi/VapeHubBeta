package com.bozdi.vapehubbeta

import android.app.Application
import com.bozdi.vapehubbeta.model.OrderListService

class App : Application(){
    val ordersService = OrderListService()
}