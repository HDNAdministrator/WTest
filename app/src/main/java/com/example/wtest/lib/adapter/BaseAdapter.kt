package com.example.wtest.lib.adapter

import androidx.lifecycle.*
import androidx.lifecycle.Lifecycle.State.*
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.*

/**
 * This generic class inherits from [LifecycleAdapter] and has [BasicViewHolder] as parameter.
 * It implements the required functionality for [BasicViewHolder] operate correctly,
 * provides handling functions for the [List] that it adapts to synchronize any change to the [List] and notification to the [RecyclerView] and
 * reduce boilerplate code for binding data to a [BasicViewHolder].
 */
abstract class BaseAdapter<VH : BasicViewHolder<out ViewBinding, T>, T: Any>(
    callback: ItemCallback<T>
) : LifecycleAdapter<VH, T>(callback) {
    override fun onViewAttachedToWindow(holder: VH) { super.onViewAttachedToWindow(holder); holder.onViewCreated() }

    override fun onViewDetachedFromWindow(holder: VH) { super.onViewDetachedFromWindow(holder); holder.onViewDestroyed() }

    override fun onBindViewHolder(holder: VH, position: Int) { with(holder) { getItem(position).setViewHolder() } }
}