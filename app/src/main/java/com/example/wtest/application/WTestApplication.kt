package com.example.wtest.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Named [Application] class required by Hilt so it can initialize
 */
@HiltAndroidApp
class WTestApplication : Application()