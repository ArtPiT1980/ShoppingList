package com.artpit.android.shoppinglist.data

import com.artpit.android.shoppinglist.domain.ShopItem
import com.artpit.android.shoppinglist.domain.ShopListRepository

class ShopListRepositoryImpl : ShopListRepository {
    private val shopList = mutableListOf<ShopItem>()
    private var autoincrementId = 0
    override fun getShopList(): List<ShopItem> {
        return shopList.toList()
    }

    override fun getShopItem(id: Int): ShopItem {
        return shopList.find { it.id == id } ?: throw RuntimeException("Element with id $id not found")
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNKNOWN_ID) {
            shopItem.id = autoincrementId++
        }

        addShopItem(shopItem)
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id)
        shopList.remove(oldElement)
        addShopItem(shopItem)
    }
}