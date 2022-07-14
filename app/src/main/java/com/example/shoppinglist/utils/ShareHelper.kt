package com.example.shoppinglist.utils

import android.content.Intent
import com.example.shoppinglist.entities.ShopListItem

object ShareHelper {
    fun shareShopList(shopList: List<ShopListItem>,listName: String): Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plane"
        intent.apply {
            putExtra(Intent.EXTRA_TEXT, makeShareText(shopList,listName))
        }
        return intent
    }

    private fun makeShareText(shopList: List<ShopListItem>,listName: String): String{
        val sBuilder = StringBuilder()
        sBuilder.append("список: $listName")
        sBuilder.append("\n")
        var couner = 0
        shopList.forEach{
            if(it.itemInfo.isEmpty()){
                sBuilder.append("${++couner} -  ${it.name}")
                sBuilder.append("\n")
            }else{
                sBuilder.append("${couner++} -  ${it.name}  (${it.itemInfo})")
                sBuilder.append("\n")
            }

        }
        return sBuilder.toString()

    }
}