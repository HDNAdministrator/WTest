package com.example.wtest.lib.viewmodels

import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.example.wtest.lib.repo.Repo

/**
 * Abstract viewmodel that provides the basics to all [ViewModel]s such as access to the apps repository [Repo]
 * and most common functionality like string from [Resources]
 */
abstract class BasicViewModel(
    private val resources: Resources,
    protected val repo: Repo
) : ViewModel() {
    fun string(@StringRes id: Int) = resources.getString(id)
}