package com.sumin.shoppinglist.domain

interface ShopListRepository {

    fun addShopList(shopItem: ShopItem)

    fun deleteShopItem(shopItem: ShopItem)

    fun editShopList(shopItem: ShopItem)

    fun getShopItem(shopItem: Int): ShopItem

    fun getShopList(): List<ShopItem>
}
