package com.example.wtest.lib.misc

import androidx.recyclerview.widget.DiffUtil.ItemCallback

@DslMarker
annotation class DiffItemDsl

/**
 * An [ItemCallback] DSL to build a callback object from the abstract class
 */
@DiffItemDsl
class DiffItem<T: Any> {

    //region vars
    private var  areItemsTheSame: ((oldItem: T, newItem: T) -> Boolean)? = null
    private var areContentsTheSame: ((oldItem: T, newItem: T) -> Boolean)? = null
    //endregion vars

    fun areItemsTheSame(block: (oldItem: T, newItem: T) -> Boolean) {
        this.areItemsTheSame = block
    }

    fun areContentsTheSame(block: (oldItem: T, newItem: T) -> Boolean) {
        this.areContentsTheSame = block
    }

    fun build(): ItemCallback<T> {
        return object : ItemCallback<T>() {
            override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = areItemsTheSame?.invoke(oldItem, newItem) ?: false

            override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = areContentsTheSame?.invoke(oldItem, newItem) ?: false
        }
    }
}