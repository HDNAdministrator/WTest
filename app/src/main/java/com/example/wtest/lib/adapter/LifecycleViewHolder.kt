package com.example.wtest.lib.adapter

import android.view.View
import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.State.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView.ViewHolder

/**
 * An abstract class that defines a [Lifecycle] for a [ViewHolder] by implementing a [LifecycleOwner]
 */
abstract class LifecycleViewHolder constructor(
    rootView: View
) : ViewHolder(rootView), LifecycleOwner {

    //region vars
    private val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)
    //endregion vars

    init { with(lifecycleRegistry) { currentState = INITIALIZED; currentState = CREATED } }

    override fun getLifecycle(): Lifecycle = lifecycleRegistry

    @CallSuper
    open fun onViewCreated() { lifecycleRegistry.apply { currentState = STARTED; currentState = RESUMED } }

    @CallSuper
    open fun onViewDestroyed() { lifecycleRegistry.currentState = DESTROYED }
}