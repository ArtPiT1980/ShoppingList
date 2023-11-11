package com.artpit.android.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import com.artpit.android.shoppinglist.data.ShopListRepositoryImpl
import com.artpit.android.shoppinglist.domain.DeleteShopItemUseCase
import com.artpit.android.shoppinglist.domain.EditShopItemUseCase
import com.artpit.android.shoppinglist.domain.GetShopListUseCase
import com.artpit.android.shoppinglist.domain.ShopItem

class MainViewModel : ViewModel() {
    private val repository = ShopListRepositoryImpl
    private val getShopListUseCase = GetShopListUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun editShopItem(shopItem: ShopItem) {
        val newShopItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(newShopItem)
    }
}