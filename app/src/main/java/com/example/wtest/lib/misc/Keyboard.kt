package com.example.wtest.lib.misc

import android.view.View
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT

/**
 * Class that simplify the usage of the soft keyboard
 */
class Keyboard(
    private val inputMethodManager: InputMethodManager,
    private val root: View
) {
    fun show() { inputMethodManager.showSoftInput(root, SHOW_IMPLICIT) }

    fun hide() { inputMethodManager.hideSoftInputFromWindow(root.windowToken, 0) }
}