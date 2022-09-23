package com.sumin.shoppinglist.presentation


import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sumin.shoppinglist.R
import com.sumin.shoppinglist.databinding.ItemShopDisabledBinding
import com.sumin.shoppinglist.databinding.ItemShopEnabledBinding
import com.sumin.shoppinglist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {
    var shoplist = listOf<ShopItem>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_shop_enabled,
                parent,
                false
            )
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shoplist[position]
        val status = if (shopItem.enabled) {
            "Active"
        } else {
            "Not active"
        }

        holder.itemView.setOnLongClickListener {
            true
        }
        if (shopItem.enabled) {
            holder.binding.tvName.text = "${shopItem.name} $status"
            holder.binding.tvCount.text = shopItem.count.toString()
            holder.binding.tvName.setTextColor(Color.RED)
        }
    }

    override fun onViewRecycled(holder: ShopItemViewHolder) {
        super.onViewRecycled(holder)
        holder.binding.tvName.text = ""
        holder.binding.tvCount.text = ""
    }

    override fun getItemCount(): Int {
        return shoplist.size
    }






    class ShopItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemShopEnabledBinding.bind(view)
    }
}
