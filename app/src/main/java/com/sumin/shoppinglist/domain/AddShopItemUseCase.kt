package com.sumin.shoppinglist.domain

class AddShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun addShopList(shopItem: ShopItem){
        shopListRepository.addShopItem(shopItem)
    }
}