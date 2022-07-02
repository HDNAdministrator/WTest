package com.example.wtest.lib.adapter

import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.State.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

/**
 * An abstract class that defines a [Lifecycle] to an [ListAdapter] class by implementing [LifecycleOwner]
 */
abstract class LifecycleAdapter<VH: ViewHolder, T: Any>(
    callback: ItemCallback<T>
) : ListAdapter<T, VH>(callback), LifecycleOwner {

    //region vars
    private val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)
    //endregion vars

    init { lifecycleRegistry.apply { currentState = INITIALIZED; currentState = CREATED } }

    override fun getLifecycle(): Lifecycle = lifecycleRegistry

    @CallSuper
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        lifecycleRegistry.apply { currentState = STARTED; currentState = RESUMED }
    }

    @CallSuper
    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        lifecycleRegistry.apply { currentState = STARTED; currentState = CREATED; currentState = DESTROYED }
    }
}