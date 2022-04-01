package com.bozdi.vapehubbeta

object GlobalVars {
    public var token : String = ""
    public var UserId : String = ""
    public var UserType : String = ""
    public var URL : String = "http://178.20.45.151:3000/api/"
    var listOrders = mutableListOf<ItemSizeDataModel>()
    data class ItemSizeDataModel(
        val OrderId: String?,
        val From: String,
        val Status: String,
        val TotalCost: String,
    )
}