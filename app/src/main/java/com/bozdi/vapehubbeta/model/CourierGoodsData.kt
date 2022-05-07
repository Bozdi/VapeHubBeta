package com.bozdi.vapehubbeta.model

@kotlinx.serialization.Serializable
data class CourierGoodsData(
    val GoodId: String?,
    val GoodLink: String?,
    val Name: String?,
    val Price: String?,
    val Available: String?
)
