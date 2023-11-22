package com.artpit.android.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.artpit.android.shoppinglist.domain.ShopItem

@Dao
interface ShopListDao {
    @Query("SELECT * FROM shop_items")
    fun getShopList(): LiveData<List<ShopItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShopItem(shopItem: ShopItemDbModel)

    @Delete
    suspend fun deleteShopItem(shopItem: ShopItemDbModel)

    @Query("DELETE FROM shop_items WHERE id=:id")
    suspend fun deleteShopItem(id: Int)

    @Query("SELECT * FROM shop_items WHERE id=:id LIMIT 1")
    suspend fun getShopItem(id: Int): ShopItemDbModel
}