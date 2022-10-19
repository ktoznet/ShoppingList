package com.example.shoppinglist.domain.usecase

import com.example.shoppinglist.domain.repository.ShopListRepository
import com.example.shoppinglist.domain.entity.ShopItem

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {
    suspend fun getShopItem(shopItemId: Int): ShopItem {
        return shopListRepository.getShopItem(shopItemId)
    }
}