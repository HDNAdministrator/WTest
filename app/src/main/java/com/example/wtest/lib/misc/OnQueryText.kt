package com.example.wtest.lib.misc

import android.widget.SearchView.OnQueryTextListener

@DslMarker
annotation class OnQueryTextDsl

/**
 * An [OnQueryTextListener] DSL to build a listener object from the interface
 */
@OnQueryTextDsl
class OnQueryText {

    //region vars
    private var  onQueryTextSubmit: ((query: String?) -> Boolean)? = null
    private var onQueryTextChange: ((query: String?) -> Boolean)? = null
    //endregion vars

    fun onQueryTextSubmit(block: (query: String?) -> Boolean) {
        this.onQueryTextSubmit = block
    }

    fun onQueryTextChange(block: (query: String?) -> Boolean) {
        this.onQueryTextChange = block
    }

    fun build(): OnQueryTextListener {
        return object : OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean = onQueryTextSubmit?.invoke(p0) ?: false

            override fun onQueryTextChange(p0: String?): Boolean = onQueryTextChange?.invoke(p0) ?: false
        }
    }
}