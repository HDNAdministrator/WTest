package com.example.wtest.splash

import android.content.res.Resources
import androidx.lifecycle.viewModelScope
import com.example.wtest.lib.repo.Repo
import com.example.wtest.lib.viewmodels.BasicViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.Lazily
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    res: Resources,
    repo: Repo
) : BasicViewModel(res, repo) {

    //region vars
    val response: Flow<Result<Unit>>
    //endregion vars

    /**
     * Immediate call to evaluate if it is required to download a zip code catalogue
     */
    init {
        this.response = repo.zipCodes()
    }
}