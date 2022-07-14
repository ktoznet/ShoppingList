package com.example.shoppinglist.db.adapter

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.NoteListItemBinding
import com.example.shoppinglist.entities.NoteItem
import com.example.shoppinglist.utils.HtmlManager
import com.example.shoppinglist.utils.TimeManager

class NoteAdapter(private val listener: Listener, private val defPref: SharedPreferences) : ListAdapter<NoteItem, NoteAdapter.Itemholder>(ItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Itemholder {
        return Itemholder.create(parent)
    }

    override fun onBindViewHolder(holder: Itemholder, position: Int) {
        holder.setdata(getItem(position),listener,defPref)
    }

    class Itemholder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = NoteListItemBinding.bind(view)

        fun setdata(note: NoteItem, listener: Listener, defPref: SharedPreferences) = with((binding)) {
            tvTitle.text = note.title
            tvDescription.text = HtmlManager.getFromhtml(note.content).trim()
            tvTime.text = TimeManager.getTimeFormat(note.time,defPref)
            itemView.setOnClickListener {
                listener.onClickItem(note)
            }
            imDelete.setOnClickListener {
                listener.deleteItem(note.id!!)
            }
        }
        companion object{
            fun create(parent:ViewGroup): Itemholder{
                return Itemholder(LayoutInflater.from(parent.context).
                inflate(R.layout.note_list_item,parent,false))
            }
        }
    }

    class ItemComparator : DiffUtil.ItemCallback<NoteItem>(){
        override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return oldItem == newItem
        }

    }
    interface Listener{
        fun deleteItem(id: Int)
        fun onClickItem(note: NoteItem)
    }
}