package com.artpit.android.shoppinglist.domain

data class ShopItem(
    val name: String,
    val count: Int,
    val enabled: Boolean,
    var id: Int = UNKNOWN_ID
) {
    companion object {
        const val UNKNOWN_ID = -1
    }
}
