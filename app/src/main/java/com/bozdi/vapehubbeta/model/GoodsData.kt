package com.bozdi.vapehubbeta.model

@kotlinx.serialization.Serializable
data class GoodsData(
    val GoodId: String?,
    val GoodLink: String?,
    val Name: String?,
    val Price: String?,
    var Available: String?,
    var defaultAvailable: Int?)