package com.sumin.shoppinglist.domain

class AddShopListUseCase(private val shopListRepository: ShopListRepository) {
    fun addShopList(shopItem: ShopItem){
        shopListRepository.addShopList(shopItem)
    }
}