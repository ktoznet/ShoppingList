package com.example.shoppinglist.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.repositoryimpl.ShopListRepositoryImpl
import com.example.shoppinglist.domain.usecase.AddShopItemUseCase
import com.example.shoppinglist.domain.usecase.EditShopItemUseCase
import com.example.shoppinglist.domain.usecase.GetShopItemUseCase
import com.example.shoppinglist.domain.entity.ShopItem

class ShopItemViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val _errorinputName = MutableLiveData<Boolean>()
    private val _errorinputCount = MutableLiveData<Boolean>()
    val errorinputName: LiveData<Boolean>
        get() = _errorinputName
    val errorinputCount: LiveData<Boolean>
        get() = _errorinputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen


    fun getShopItem(shopItemId: Int) {
        val item = getShopItemUseCase.getShopItem(shopItemId)
        _shopItem.value = item

    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            val shopItem = ShopItem(name, count, true)
            addShopItemUseCase.addShopItem(shopItem)
            finishWork()
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            _shopItem.value?.let {
                val item = it.copy(name = name, count = count)
                editShopItemUseCase.editShopItem(item)
                finishWork()
            }

        }

    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorinputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorinputCount.value = true
            result = false
        }
        return result
    }

     fun resetErrorInputName() {
        _errorinputName.value = false
    }

     fun resetErrorInpuCount() {
        _errorinputCount.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}