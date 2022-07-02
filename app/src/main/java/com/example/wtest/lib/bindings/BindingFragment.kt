package com.example.wtest.lib.bindings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.OnBackPressedDispatcher
import androidx.annotation.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.wtest.lib.misc.Keyboard
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

/**
 * Abstract fragment that handles it layout using [ViewBinding] `inflate`
 * and provides basic functionality like navigation commands
 * and access to commonly used functions from [Resources].
 */
abstract class BindingFragment<out VB : ViewBinding>(private val inflate: (LayoutInflater) -> VB) : Fragment() {

    //region vars
    @Inject lateinit var inputMethodManager: InputMethodManager
    private var mutableKeyboard: Keyboard? = null
    private var nullableBinding: VB? = null
    protected var snackbar: Snackbar? = null
    protected val keyboard: Keyboard; get() = mutableKeyboard!!
    protected val binding: VB?; get() = nullableBinding
    protected val onBackPressedDispatcher: OnBackPressedDispatcher; get() = requireActivity().onBackPressedDispatcher
    //endregion vars

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflate(inflater)
            .also { this.nullableBinding = it }
            .root
            .also { this.mutableKeyboard = Keyboard(inputMethodManager, it) }
    }

    override fun onDestroyView() { this.nullableBinding = null; this.mutableKeyboard = null; super.onDestroyView() }

    protected fun string(@StringRes id: Int) = resources.getString(id)

    protected fun toast(@StringRes id: Int) { Toast.makeText(requireContext(), string(id), LENGTH_LONG).show() }

    protected fun Fragment.navigateUp() { findNavController().navigateUp() }

    protected fun Fragment.navigateTo(directions: NavDirections?, navOptions: NavOptions? = null) {
        directions?.let { findNavController().takeIf { it.currentDestination?.run { getAction(directions.actionId)?.destinationId?.let { it != id } } == true }?.navigate(it, navOptions) }
    }

    protected fun Fragment.popBack() { findNavController().popBackStack() }

    protected fun Fragment.popBackTo(@IdRes destinationId: Int, inclusive: Boolean = true) { findNavController().popBackStack(destinationId, inclusive) }
}