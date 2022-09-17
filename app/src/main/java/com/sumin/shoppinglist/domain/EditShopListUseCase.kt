package com.sumin.shoppinglist.domain

class EditShopListUseCase(private val shopListRepository: ShopListRepository) {
    fun editShopList(shopItem: ShopItem){
        shopListRepository.editShopList(shopItem)
    }
}