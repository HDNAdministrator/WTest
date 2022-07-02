package com.example.wtest.foundation

import com.example.wtest.databinding.ActivityFoundationBinding
import com.example.wtest.lib.bindings.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * Primary activity that hold the app main navigation graph
 */
@AndroidEntryPoint
class FoundationActivity : BindingActivity<ActivityFoundationBinding>(ActivityFoundationBinding::inflate)