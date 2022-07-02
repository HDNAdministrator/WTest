package com.example.wtest.splash

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import androidx.fragment.app.viewModels
import com.example.wtest.R
import com.example.wtest.databinding.FragmentSplashBinding
import com.example.wtest.lib.bindings.BindingFragment
import com.example.wtest.lib.misc.observe
import dagger.hilt.android.AndroidEntryPoint

/**
 * Initial Fragment presented to the user and will handle an one time, if successful, catalogue download and navigate forward once successful
 */
@AndroidEntryPoint
class SplashFragment : BindingFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    //region vars
    private val splashViewModel: SplashViewModel by viewModels()
    //endregion vars

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        splashViewModel
            .response
            .observe(viewLifecycleOwner) {
                it
                    .onSuccess { navigateTo(SplashFragmentDirections.actionSplashFragmentToSearchFragment()) }
                    .onFailure { toast(R.string.failToLoadData); binding?.barSplashLoading?.visibility = GONE }
            }
    }
}