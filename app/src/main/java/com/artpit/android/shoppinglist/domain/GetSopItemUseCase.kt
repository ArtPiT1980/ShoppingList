package com.artpit.android.shoppinglist.domain

class GetSopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun getShopItem(id: Int): ShopItem {
        return shopListRepository.getShopItem(id)
    }
}