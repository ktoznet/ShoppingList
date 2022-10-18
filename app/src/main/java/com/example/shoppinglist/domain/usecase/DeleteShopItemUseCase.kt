package com.example.shoppinglist.domain.usecase

import com.example.shoppinglist.domain.repository.ShopListRepository
import com.example.shoppinglist.domain.entity.ShopItem

class DeleteShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun deleteShopItem(shopItem: ShopItem) {
        shopListRepository.deleteShopItem(shopItem)
    }
}