package com.example.shoppinglist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.repositoryimpl.ShopListRepositoryImpl
import com.example.shoppinglist.domain.usecase.DeleteShopItemUseCase
import com.example.shoppinglist.domain.usecase.EditShopItemUseCase
import com.example.shoppinglist.domain.usecase.GetShopListUseCase
import com.example.shoppinglist.domain.entity.ShopItem


class MainViewModel: ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem) {
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(newItem)
    }
}