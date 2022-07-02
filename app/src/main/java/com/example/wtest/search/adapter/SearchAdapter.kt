package com.example.wtest.search.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.example.wtest.lib.adapter.BaseAdapter
import com.example.wtest.lib.repo.room.Address
import com.example.wtest.lib.repo.room.AddressFTS
import com.example.wtest.lib.repo.room.AddressWithMatchInfo

/**
 * An [Adapter] class that adapts a list of [Address] for a [RecyclerView]
 */
class SearchAdapter(
    callback: ItemCallback<Address>
) : BaseAdapter<SearchViewHolder, Address>(callback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder = SearchViewHolder(parent)
}