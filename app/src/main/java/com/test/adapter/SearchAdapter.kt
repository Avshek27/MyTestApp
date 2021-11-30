package com.test.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult.NO_POSITION
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.test.R
import com.test.storage.LocationSearch

class SearchAdapter(
    val listener: SearchItemClickInterface
) :
    ListAdapter<LocationSearch, SearchAdapter.LocationHolder>(diffCallback) {

    inner class LocationHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvId: TextView = itemView.findViewById(R.id.tv_id)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvType: TextView = itemView.findViewById(R.id.tv_type)
        val delete: ImageButton = itemView.findViewById(R.id.delete_record)

        init {
            itemView.setOnClickListener {
                if (adapterPosition != NO_POSITION)
                    listener.onSearchItemClick(getItem(adapterPosition))
            }

            delete.setOnClickListener {
                if (adapterPosition != NO_POSITION)
                listener.onDeleteButtonClick(getItem(adapterPosition))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.common_list_item, parent, false
        )
        return LocationHolder(itemView)
    }

    override fun onBindViewHolder(holder: LocationHolder, position: Int) {
        with(getItem(position)) {
            holder.tvTitle.text = title
            holder.tvId.text = woeid.toString()
            holder.tvType.text = location_type

            if (timeStamp != null)
                holder.delete.visibility = View.VISIBLE
        }
    }
}

private val diffCallback = object : DiffUtil.ItemCallback<LocationSearch>() {
    override fun areItemsTheSame(oldItem: LocationSearch, newItem: LocationSearch) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: LocationSearch, newItem: LocationSearch) =
        oldItem.title == newItem.title && oldItem.location_type == newItem.location_type && oldItem.woeid == newItem.woeid
}

interface SearchItemClickInterface {
    // creating a method for click action
    // on recycler view item for storing in DB.
    fun onSearchItemClick(item: LocationSearch)
    // to delete record
    fun onDeleteButtonClick(item: LocationSearch)
}