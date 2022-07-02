package com.example.wtest.search

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.example.wtest.lib.misc.diffCallback
import com.example.wtest.lib.repo.room.Address
import com.example.wtest.lib.repo.room.AddressFTS
import com.example.wtest.lib.repo.room.AddressWithMatchInfo
import com.example.wtest.search.adapter.SearchAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object SearchModule {
    /**
     * Provides an [ItemCallback] with [Address] as parameter by calling the DSL [DiffCallback]
     */
    @FragmentScoped
    @Provides
    fun providesItemCallback(): ItemCallback<Address> {
        return diffCallback {
            areContentsTheSame { oldItem, newItem -> oldItem.rowid == newItem.rowid }

            areItemsTheSame { oldItem, newItem -> oldItem == newItem }
        }
    }

    /**
     * Provides a SearchAdapter using a [ItemCallback] for comparison
     */
    @FragmentScoped
    @Provides
    fun providesSearchAdapter(callback: ItemCallback<Address>): SearchAdapter = SearchAdapter(callback)
}