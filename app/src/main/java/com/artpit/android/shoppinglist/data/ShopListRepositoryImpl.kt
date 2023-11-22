package com.artpit.android.shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.artpit.android.shoppinglist.domain.ShopItem
import com.artpit.android.shoppinglist.domain.ShopListRepository

class ShopListRepositoryImpl(application: Application) : ShopListRepository {
    private val shopListDao = AppDatabase.getInstance(application).shopListDao()
    private val mapper = ShopListMapper()

    override suspend fun addShopItem(shopItem: ShopItem) {
        //val newShopItem = shopItem.copy(id = 0)
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun deleteShopItem(id: Int) {
        shopListDao.deleteShopItem(id)
    }

    override suspend fun getShopItem(id: Int): ShopItem {
        return mapper.mapDbModelToEntity(shopListDao.getShopItem(id))
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        addShopItem(shopItem)
    }

    override fun getShopList(): LiveData<List<ShopItem>> =
        shopListDao.getShopList().map { mapper.mapListDbModelToListEntity(it) }

//    override fun getShopList(): LiveData<List<ShopItem>> =
//        MediatorLiveData<List<ShopItem>>().apply {
//            addSource(shopListDao.getShopList()) {
//                mapper.mapListDbModelToListEntity(it)
//            }
//        }

}