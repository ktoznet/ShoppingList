package com.example.shoppinglist.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shoppinglist.domain.entity.ShopItem

@Entity(tableName = "shop_items")
data class ShopItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val count: Int,
    val enabled: Boolean
)