package com.artpit.android.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
    fun getShopList(): LiveData<List<ShopItem>>
    suspend fun getShopItem(id: Int): ShopItem
    suspend fun addShopItem(shopItem: ShopItem)
    suspend fun deleteShopItem(shopItem: ShopItem)
    suspend fun deleteShopItem(id: Int)
    suspend fun editShopItem(shopItem: ShopItem)
}