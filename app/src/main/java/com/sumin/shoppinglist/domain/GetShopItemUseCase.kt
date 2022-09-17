package com.sumin.shoppinglist.domain

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun getShopItem(shopItem: Int): ShopItem{
        return shopListRepository.getShopItem(shopItem)
    }
}