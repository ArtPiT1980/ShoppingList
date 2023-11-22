package com.artpit.android.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.artpit.android.shoppinglist.data.ShopListRepositoryImpl
import com.artpit.android.shoppinglist.domain.DeleteShopItemUseCase
import com.artpit.android.shoppinglist.domain.EditShopItemUseCase
import com.artpit.android.shoppinglist.domain.GetShopListUseCase
import com.artpit.android.shoppinglist.domain.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ShopListRepositoryImpl(application)
    private val getShopListUseCase = GetShopListUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)

    private val scope = CoroutineScope(Dispatchers.IO)
    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        scope.launch {
            deleteShopItemUseCase.deleteShopItem(shopItem)
        }

    }

    fun editShopItem(shopItem: ShopItem) {
        scope.launch {
            val newShopItem = shopItem.copy(enabled = !shopItem.enabled)
            editShopItemUseCase.editShopItem(newShopItem)
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}