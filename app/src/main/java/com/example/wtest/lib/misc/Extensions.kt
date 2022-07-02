package com.example.wtest.lib.misc

import android.app.Service
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.lifecycle.Lifecycle.State
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.Lifecycle.State.CREATED
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.text.Normalizer
import java.text.Normalizer.normalize
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Reduce boilerplate code necessary to collect a flow on a [LifecycleOwner] while allowing a different [CoroutineContext] and/or [State] from the default at the same time
 */
inline fun <reified T> Flow<T>.observe(owner: LifecycleOwner, context: CoroutineContext = EmptyCoroutineContext, state: State = if (owner is Service) STARTED else CREATED, crossinline collector: CoroutineScope.(value: T) -> Unit) {
    with(owner) { lifecycleScope.launch(context) { repeatOnLifecycle(state) { collect { this.collector(it) } } } }
}

/**
 * Returns a [OnQueryTextListener] object from a lambda with a [OnQueryTextListener] DSL as context
 */
fun onQueryTextListener(supplier: OnQueryText.() -> Unit): OnQueryTextListener = OnQueryText().apply(supplier).build()

/**
 * Returns an [ItemCallback] object using a generic lambda with a [DiffItem] DSL as context
 */
fun <T: Any>diffCallback(block: DiffItem<T>.() -> Unit): ItemCallback<T> = DiffItem<T>().apply(block).build()

/**
 * Reduce the boilerplate for launching a coroutine while allowing normal configurability
 */
fun ViewModel.launch(context: CoroutineContext = EmptyCoroutineContext, block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch(context = context, block = block)
}

/**
 * Reduce the boilerplate for launching a coroutine attach to the fragment **view** lifecycle while allowing normal configurability
 */
fun Fragment.launch(context: CoroutineContext = EmptyCoroutineContext, block: suspend CoroutineScope.() -> Unit): Job {
    return viewLifecycleOwner.lifecycleScope.launch(context = context, block = block)
}

fun String.removeNonSpacingMarks() = normalize(this, Normalizer.Form.NFD).replace("\\p{Mn}+".toRegex(), "")
