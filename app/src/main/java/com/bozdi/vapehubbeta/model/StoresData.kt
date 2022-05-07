package com.bozdi.vapehubbeta.model

@kotlinx.serialization.Serializable
data class StoresData(
    val StoreId: String,
    var CityId: String?,
    val CityLink: String?,
    var Street: String?,
    var BuildingNumber: String?,
    val PlaceId: String?,
)
