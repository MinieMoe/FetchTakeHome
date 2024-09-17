package com.example.fetchtakehome
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

sealed class ListDisplayItem {
    data class Header(val listId: Int): ListDisplayItem()
    data class Item(val listItem: ListItem): ListDisplayItem()
}

class ListAdapter(private val displayItems: MutableList<ListDisplayItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        companion object {
            private const val VIEW_TYPE_HEADER = 0
            private const val VIEW_TYPE_ITEM = 1
        }

        class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val headerListId : TextView = itemView.findViewById(R.id.header_list_id)

            fun bind(header: ListDisplayItem.Header) {
                headerListId.text = "List ID: ${header.listId}"
            }
        }

        class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val itemId : TextView = itemView.findViewById(R.id.idView)
            val listItemId: TextView = itemView.findViewById(R.id.listIdView)
            val listItemName: TextView = itemView.findViewById(R.id.itemNameView)

            fun bind(item: ListDisplayItem.Item) {
                itemId.text = "id: ${item.listItem.id}"
                listItemId.text = "listId: ${item.listItem.listId}"
                listItemName.text = "name: ${item.listItem.name}"
            }
        }

        override fun getItemViewType(position: Int): Int {
            return when (displayItems[position]) {
                is ListDisplayItem.Header -> VIEW_TYPE_HEADER
                is ListDisplayItem.Item -> VIEW_TYPE_ITEM
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return if (viewType == VIEW_TYPE_HEADER) {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.list_id_header,parent,false)
                HeaderViewHolder(view)
            } else {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
                ItemViewHolder(view)
            }
        }

        // insert data from api into the item via holder
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (holder) {
                is HeaderViewHolder -> {
                    val header = displayItems[position] as ListDisplayItem.Header
                    holder.bind(header)
                }
                is ItemViewHolder -> {
                    val item = displayItems[position] as ListDisplayItem.Item
                    holder.bind(item)
                }
            }
        }

        override fun getItemCount(): Int = displayItems.size

    // Method to update data
    fun updateData(newDisplayItems: MutableList<ListDisplayItem>) {
        displayItems.clear()
        displayItems.addAll(newDisplayItems)
        notifyDataSetChanged()
    }
}