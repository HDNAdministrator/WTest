package com.example.wtest.lib.bindings

import android.app.Activity
import android.os.Bundle
import android.view.Gravity.TOP
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.*
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.example.wtest.R
import com.example.wtest.application.Connectivity
import com.example.wtest.lib.misc.observe
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

/**
 * Abstract activity that handles it layout using [ViewBinding] `inflate`
 * and provides basic functionality like connectivity status indication,
 * the same navigation commands available on a [Fragment]
 * and access to commonly used functions from [Resources].
 *
 * NOTE: Is it required to have a [FragmentContainerView] with a [NavHostFragment] on the layout used by the activity
 */
abstract class BindingActivity<out VB: ViewBinding>(private val inflate: (LayoutInflater) -> VB) : AppCompatActivity() {

    //region vars
    @Inject @Connectivity lateinit var connectivity: Flow<Boolean>
    private var nullableBinding: VB? = null
    protected var snackbar: Snackbar? = null
    var navController: NavController? = null
    val binding: VB; get() = nullableBinding!!
    //endregion vars

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inflate
            .invoke(layoutInflater)
            .let {
                this.nullableBinding = it

                it.root
                    .let {
                        setContentView(it)

                        recursiveFragmentContainerViewSearch(it as ViewGroup)
                    }
            }

        /**
         * Observe the internet connectivity and notify the user accordingly
         */
        connectivity
            .observe(this) { this@BindingActivity.snackbar = if (it) snackbar?.run { dismiss(); null } else createSnackBar(binding.root) }
    }

    override fun onDestroy() { this.nullableBinding = null; super.onDestroy() }

    protected fun string(@StringRes id: Int) = resources.getString(id)

    /**
     * Attempts to navigate up in the navigation hierarchy. Suitable for when the
     * user presses the "Up" button marked with a left (or start)-facing arrow in the upper left
     * (or starting) corner of the app UI.
     *
     * The intended behavior of Up differs from [Back][popBackStack] when the user
     * did not reach the current destination from the application's own task. e.g. if the user
     * is viewing a document or link in the current app in an activity hosted on another app's
     * task where the user clicked the link. In this case the current activity (determined by the
     * context used to create this NavController) will be [finished][Activity.finish] and
     * the user will be taken to an appropriate destination in this app on its own task.
     *
     * @return true if navigation was successful, false otherwise
     */
    protected fun navigateUp(): Boolean? = navController?.navigateUp()

    /**
     * Navigate via the given [NavDirections]
     *
     * @param directions directions that describe this navigation operation
     * @param navOptions special options for this navigation operation
     */
    protected fun navigateTo(directions: NavDirections, navOptions: NavOptions? = null) {
        navController?.takeIf { it.currentDestination?.run { getAction(directions.actionId)?.destinationId?.let { it != id } } == true }?.navigate(directions, navOptions)
    }

    /**
     * Attempts to pop the controller's back stack. Analogous to when the user presses
     * the system [Back][android.view.KeyEvent.KEYCODE_BACK] button when the associated
     * navigation host has focus.
     *
     * @return true if the stack was popped at least once and the user has been navigated to
     * another destination, false otherwise
     */
    protected fun popBack() = navController?.popBackStack()

    /**
     * Attempts to pop the controller's back stack back to a specific destination.
     *
     * @param destinationId The topmost destination to retain
     * @param inclusive Whether the given destination should also be popped.
     *
     * @return true if the stack was popped at least once and the user has been navigated to
     * another destination, false otherwise
     */
    protected fun popBackTo(@IdRes destinationId: Int, inclusive: Boolean = true) = navController?.popBackStack(destinationId, inclusive)

    /**
     * Execute a recursive search for the nested [FragmentContainerView] and fetch the [NavController]
     */
    private fun recursiveFragmentContainerViewSearch(viewGroup: ViewGroup): Boolean {
        if (viewGroup is FragmentContainerView) { assignNavController(viewGroup.id); return true }
        else {
            viewGroup
                .forEach {
                    if (it is FragmentContainerView) { assignNavController(it.id); return true }
                    else if (it is ViewGroup) { if (recursiveFragmentContainerViewSearch(it)) { return true } }
                }

            return false
        }
    }

    private fun createSnackBar(v: View): Snackbar {
        return Snackbar.make(v, string(R.string.noInternet), LENGTH_INDEFINITE)
            .apply { view.layoutParams = (view.layoutParams as FrameLayout.LayoutParams).apply { gravity = TOP }; show() }
    }

    /**
     * Fetch the [NavController] given an [View] id.
     */
    private fun assignNavController(@IdRes id: Int) { this.navController = (supportFragmentManager.findFragmentById(id) as NavHostFragment).navController }
}