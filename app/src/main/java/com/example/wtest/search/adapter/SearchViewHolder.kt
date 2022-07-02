package com.example.wtest.search.adapter

import android.view.ViewGroup
import com.example.wtest.databinding.ViewholderResultBinding
import com.example.wtest.lib.adapter.BasicViewHolder
import com.example.wtest.lib.repo.room.Address
import com.example.wtest.lib.repo.room.AddressFTS
import com.example.wtest.lib.repo.room.AddressWithMatchInfo

/**
 * Class that inherits from [BasicViewHolder] and it is pair with [SearchAdapter].
 */
class SearchViewHolder(
    parent: ViewGroup
) : BasicViewHolder<ViewholderResultBinding, Address>(parent, ViewholderResultBinding::inflate) {

    /**
     * Sets the [MaterialTextView] text from the provided data pass as a context
     */
    override fun Address.setViewHolder() {
        binding.lblResultName.text = toString()
    }
}