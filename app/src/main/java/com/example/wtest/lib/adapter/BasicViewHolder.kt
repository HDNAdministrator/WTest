package com.example.wtest.lib.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

/**
 * This abstract class inherits from [BindingViewHolder] and adds basic structure to be implemented by its children
 */
abstract class BasicViewHolder<out VB: ViewBinding, in T> constructor(
    parent: ViewGroup,
    inflate: (LayoutInflater, ViewGroup, Boolean) -> VB
) : BindingViewHolder<VB>(inflate, parent) {
    /**
     * An abstract function to set the viewholder layout from data pass as context
     */
    abstract fun T.setViewHolder()
}