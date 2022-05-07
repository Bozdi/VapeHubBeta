package com.bozdi.vapehubbeta.model

@kotlinx.serialization.Serializable
data class ManagersData(
    val UserId: String,
    var Login: String,
    var StoreId: String,
    val StoreLink: String,
    var Name: String,
    var Phone: String,
    val Type: String,
    val Status: String
)
