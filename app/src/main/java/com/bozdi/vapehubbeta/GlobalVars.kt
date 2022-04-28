package com.bozdi.vapehubbeta

object GlobalVars {
    var token : String = ""
    var Login : String = ""
    var ProfileName : String = ""
    var ProfilePhoneNumber : String = ""
    var ProfileStoreName: String = "";
    var UserId : String = ""
    var UserType : String = ""
    var StoreId : Int = -1
    var CityId: Int = 3
    var URL : String = "http://5.180.183.54:3000/api/"
    var listOrders = mutableListOf<ItemSizeDataModel>()
    data class ItemSizeDataModel(
        val OrderId: String?,
        val From: String,
        val Status: String,
        val TotalCost: String,
    )
}