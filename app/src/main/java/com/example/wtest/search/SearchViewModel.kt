package com.example.wtest.search

import android.content.res.Resources
import com.example.wtest.lib.misc.launch
import com.example.wtest.lib.misc.removeNonSpacingMarks
import com.example.wtest.lib.repo.Repo
import com.example.wtest.lib.repo.room.Address
import com.example.wtest.lib.repo.room.AddressFTS
import com.example.wtest.lib.repo.room.AddressWithMatchInfo
import com.example.wtest.lib.viewmodels.BasicViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    res: Resources,
    repo: Repo
) : BasicViewModel(res, repo) {

    //region vars
    private val mutableSearch: MutableSharedFlow<String> = MutableSharedFlow()
    val result: Flow<List<Address>>
    //endregion vars

    init {
        this.result = mutableSearch
            .flatMapLatest { repo.search(it) }
    }

    /**
     * Normalize the input query and launches a coroutine using the normalized version
     *
     * NOTE: Although the desired results are achieved the normalized query is not perfect
     * because it should be an "AND" operator instead of the "OR" for improve result quality.
     * It appeares [Room] doesn't support it and an alternative was not found
     */
    fun search(query: String) {
        launch {
            query
                .trim()
                .split("\\s+".toRegex())
                .joinToString("* OR *", "*", "*" ) { it.lowercase() }
                .removeNonSpacingMarks()
                .let { mutableSearch.emit(it) }
        }
    }
}