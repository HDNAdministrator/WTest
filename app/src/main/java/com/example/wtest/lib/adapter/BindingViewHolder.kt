package com.example.wtest.lib.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding

/**
 * An abstract [ViewHolder] inherited class that inflates it layout and holds a reference to be used by any other class that inherits from this class
 */
abstract class BindingViewHolder<out VB : ViewBinding> constructor(
    inflate: (LayoutInflater, ViewGroup, Boolean) -> VB,
    parent: ViewGroup,
    protected val binding: VB = inflate(LayoutInflater.from(parent.context), parent, false)
) : LifecycleViewHolder(binding.root)