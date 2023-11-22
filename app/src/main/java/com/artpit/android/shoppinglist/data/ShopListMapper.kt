package com.artpit.android.shoppinglist.data

import com.artpit.android.shoppinglist.domain.ShopItem

class ShopListMapper {
    fun mapEntityToDbModel(item: ShopItem) = ShopItemDbModel(
        id = item.id,
        name = item.name,
        count = item.count,
        enabled = item.enabled
    )

    fun mapDbModelToEntity(item: ShopItemDbModel) = ShopItem(
        id = item.id,
        name = item.name,
        count = item.count,
        enabled = item.enabled
    )

    fun mapListDbModelToListEntity(list: List<ShopItemDbModel>) = list.map {
        mapDbModelToEntity(it)
    }
}