package com.example.shoppinglist.db.adapter

import android.content.Context
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ListNameItemBinding
import com.example.shoppinglist.entities.ShopListNameItem
import com.example.shoppinglist.utils.TimeManager

class ShopListNameAdapter(private val listener: Listener, private val defPref: SharedPreferences) :
    ListAdapter<ShopListNameItem, ShopListNameAdapter.Itemholder>(ItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Itemholder {
        return Itemholder.create(parent)
    }

    override fun onBindViewHolder(holder: Itemholder, position: Int) {
        holder.setdata(getItem(position), listener,defPref)
    }

    class Itemholder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListNameItemBinding.bind(view)

        fun setdata(shopListNameItem: ShopListNameItem, listener: Listener,defPref: SharedPreferences) = with((binding)) {
            tvListName.text = shopListNameItem.name
            tvTime.text = TimeManager.getTimeFormat(shopListNameItem.time,defPref)
            pBar.max = shopListNameItem.allItemCounter
            pBar.progress = shopListNameItem.checkedItemsCounter
            val colorState = ColorStateList.valueOf(
                getProgressColorState(
                    shopListNameItem,
                    binding.root.context
                )
            )
            pBar.progressTintList = colorState
            counterCard.backgroundTintList = colorState
            var counterText =
                "${shopListNameItem.checkedItemsCounter}/${shopListNameItem.allItemCounter}"
            tvCounter.text = counterText
            itemView.setOnClickListener {
                listener.onClickItem(shopListNameItem)
            }
            imDelete.setOnClickListener {
                listener.deleteItem(shopListNameItem.id!!)
            }

            imEdit.setOnClickListener {
                listener.editItem(shopListNameItem)
            }
        }

        private fun getProgressColorState(item: ShopListNameItem, context: Context): Int {
            return if (item.checkedItemsCounter == item.allItemCounter) {
                ContextCompat.getColor(context, R.color.geenMain)
            } else {
                ContextCompat.getColor(context, R.color.red_light)
            }
        }

        companion object {
            fun create(parent: ViewGroup): Itemholder {
                return Itemholder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_name_item, parent, false)
                )
            }
        }
    }

    class ItemComparator : DiffUtil.ItemCallback<ShopListNameItem>() {
        override fun areItemsTheSame(
            oldItem: ShopListNameItem,
            newItem: ShopListNameItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ShopListNameItem,
            newItem: ShopListNameItem
        ): Boolean {
            return oldItem == newItem
        }

    }

    interface Listener {
        fun deleteItem(id: Int)
        fun editItem(shoplistName: ShopListNameItem)
        fun onClickItem(shopListName: ShopListNameItem)
    }
}