package com.example.wtest.search

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.example.wtest.R
import com.example.wtest.databinding.FragmentSearchBinding
import com.example.wtest.lib.bindings.BindingFragment
import com.example.wtest.lib.misc.observe
import com.example.wtest.lib.misc.onQueryTextListener
import com.example.wtest.search.adapter.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * The [Fragment] that handles zip codes searches against the local database
 */
@AndroidEntryPoint
class SearchFragment : BindingFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    //region vars
    @Inject lateinit var searchAdapter: SearchAdapter
    private val searchViewModel: SearchViewModel by viewModels()
    //endregion vars

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding!!) {
            with(searchViewModel) {
                txtSearchSearch
                    .setOnQueryTextListener(
                        onQueryTextListener {
                            onQueryTextSubmit {
                                hideLabel()

                                it
                                    ?.takeUnless { it.isBlank() }
                                    ?.let { barSearchLoading.visibility = VISIBLE; search(it) }
                                    ?: run { barSearchLoading.visibility = GONE; showLabel(); searchAdapter.submitList(emptyList()) }

                                true
                            }
                        }
                    )

                recSearchResults
                    .adapter = searchAdapter

                result
                    .observe(viewLifecycleOwner) {
                        barSearchLoading.visibility = GONE

                        if (it.isEmpty()) showLabel()
                        else { hideLabel(); searchAdapter.submitList(it) }
                    }
            }
        }
    }

    private fun showLabel() { binding?.lblSearchDisplay?.visibility = VISIBLE }

    private fun hideLabel() { binding?.lblSearchDisplay?.visibility = GONE }
}