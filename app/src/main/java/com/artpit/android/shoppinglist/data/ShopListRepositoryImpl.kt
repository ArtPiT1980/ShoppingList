package com.artpit.android.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.artpit.android.shoppinglist.domain.ShopItem
import com.artpit.android.shoppinglist.domain.ShopListRepository
import kotlin.random.Random

object ShopListRepositoryImpl : ShopListRepository {
    private val shopListLD = MutableLiveData<List<ShopItem>>()
    private val shopList = sortedSetOf<ShopItem>({ o1, o2 -> o1.id.compareTo(o2.id) })
    private var autoincrementId = 0

    init {
        for (i in 0..<15) {
            val shopItem = ShopItem(
                name = "name $i",
                count = i,
                enabled = Random.nextBoolean()
            )

            addShopItem(shopItem)
        }
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD
    }

    override fun getShopItem(id: Int): ShopItem {
        return shopList.find { it.id == id }
            ?: throw RuntimeException("Element with id $id not found")
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNKNOWN_ID) {
            shopItem.id = autoincrementId++
        }
        shopList.add(shopItem)
        updateShopListLD()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateShopListLD()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id)
        shopList.remove(oldElement)
        addShopItem(shopItem)
    }

    private fun updateShopListLD() {
        shopListLD.value = shopList.toList()
    }
}