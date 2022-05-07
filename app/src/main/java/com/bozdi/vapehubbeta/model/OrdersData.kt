package com.bozdi.vapehubbeta.model

@kotlinx.serialization.Serializable
data class OrdersData (
    var OrderId: String,
    val OrderLink: String?,
    var ClientName: String?,
    val StoreId: String,
    val StoreLink: String?,
    val UserId: String?,
    var ClientPhone: String?,
    var StreetName: String?,
    var BuildingNum: String?,
    var ApartNum: String?,
    var EntranceNum: String?,
    val TargetTime: String?,
    var Status: String?,
    val GMapPlaceID: String?,
    val Lat: String?,
    val Lng: String?,
    val TotalCost: String?,
    val DeliveryCost: String?,
    val GoodsTotalCost: String?,
    val CashPayment: String?,
    val CardPayment: String?,
    val PayedFromCourier: String?,
    val DeliveredTime: String?,
    val CreateAt: String?,
)