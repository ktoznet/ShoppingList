package com.example.shoppinglist.db.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ListNameItemBinding
import com.example.shoppinglist.databinding.ShopLibraryListItemBinding
import com.example.shoppinglist.databinding.ShopListItemBinding
import com.example.shoppinglist.entities.ShopListNameItem
import com.example.shoppinglist.entities.ShopListItem

class ShopListItemAdapter(private val listener: Listener) :
    ListAdapter<ShopListItem, ShopListItemAdapter.Itemholder>(ItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Itemholder {
        return if (viewType == 0) {
            Itemholder.createShopItem(parent)
        } else {
            Itemholder.createLibraryItem(parent)
        }
    }

    override fun onBindViewHolder(holder: Itemholder, position: Int) {
        if (getItem(position).itemType == 0) {
            holder.setItemData(getItem(position), listener)
        } else {
            holder.setLibraryData(getItem(position), listener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).itemType
    }

    class Itemholder(val view: View) : RecyclerView.ViewHolder(view) {


        fun setItemData(shopListItem: ShopListItem, listener: Listener) {
            val binding = ShopListItemBinding.bind(view)
            binding.apply {
                tvName.text = shopListItem.name
                tvInfo.text = shopListItem.itemInfo
                tvInfo.visibility = infoVisibility(shopListItem)
                chBox.isChecked = shopListItem.itemChecked
                setPaintAndColor(binding)
                chBox.setOnClickListener {
                    listener.onClickItem(shopListItem.copy(itemChecked = chBox.isChecked), CHECK_BOX)
                }
                imEdit.setOnClickListener {
                    listener.onClickItem(shopListItem, EDIT)
                }
            }
        }

        fun setLibraryData(shopListItem: ShopListItem, listener: Listener) {
            val binding = ShopLibraryListItemBinding.bind(view)
            binding.apply {
                tvName.text = shopListItem.name
                imEdit.setOnClickListener {
                    listener.onClickItem(shopListItem, EDIT_LIBRARY_ITEM)
                }
                imDelete.setOnClickListener {
                    listener.onClickItem(shopListItem, DELETE_LIBRARY_ITEM)
                }

                itemView.setOnClickListener {
                    listener.onClickItem(shopListItem, ADD_LIBRARY_ITEM)
                }
            }
        }

        private fun setPaintAndColor(binding: ShopListItemBinding){
            binding.apply {
                if(chBox.isChecked){
                    tvName.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    tvName.setTextColor(ContextCompat.getColor(binding.root.context,R.color.grey_light))
                    tvInfo.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    tvInfo.setTextColor(ContextCompat.getColor(binding.root.context,R.color.grey_light))
                }else{
                    tvName.paintFlags = Paint.ANTI_ALIAS_FLAG
                    tvName.setTextColor(ContextCompat.getColor(binding.root.context,R.color.black))
                    tvInfo.paintFlags = Paint.ANTI_ALIAS_FLAG
                    tvInfo.setTextColor(ContextCompat.getColor(binding.root.context,R.color.black))
                }
            }
        }

        private fun infoVisibility(shopListItem: ShopListItem): Int{
            return if(shopListItem.itemInfo.isEmpty()){
                View.GONE
            }else{
                View.VISIBLE
            }
        }

        companion object {
            fun createShopItem(parent: ViewGroup): Itemholder {
                return Itemholder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.shop_list_item, parent, false)
                )
            }

            fun createLibraryItem(parent: ViewGroup): Itemholder {
                return Itemholder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.shop_library_list_item, parent, false)
                )
            }
        }
    }

    class ItemComparator : DiffUtil.ItemCallback<ShopListItem>() {
        override fun areItemsTheSame(
            oldItem: ShopListItem,
            newItem: ShopListItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ShopListItem,
            newItem: ShopListItem
        ): Boolean {
            return oldItem == newItem
        }

    }

    interface Listener {
        fun onClickItem(shopListItem: ShopListItem, state: Int)
    }
    companion object{
        const val EDIT = 0
        const val CHECK_BOX = 1
        const val EDIT_LIBRARY_ITEM = 2
        const val DELETE_LIBRARY_ITEM = 3
        const val ADD_LIBRARY_ITEM = 4
    }
}