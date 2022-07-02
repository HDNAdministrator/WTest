package com.example.wtest.application

import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.view.inputmethod.InputMethodManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Connectivity

@Module
@InstallIn(SingletonComponent::class)
object WTestApplicationModule {

    /**
     * Provides an [InputMethodManager] to be injected in [BindingFragment] which is necessary for the [Keyboard] class
     */
    @Singleton
    @Provides
    fun providesInputMethodManager(@ApplicationContext context: Context): InputMethodManager = context.getSystemService(InputMethodManager::class.java)

    @Singleton
    @Provides
    fun providesConnectivityManager(@ApplicationContext context: Context): ConnectivityManager =  context.getSystemService(ConnectivityManager::class.java)

    /**
     * Provides a boolean flow indicating if there is an internet connection or not by observing the [ConnectivityManager]
     */
    @Connectivity
    @Singleton
    @Provides
    fun providesConnectivity(connectivityManager: ConnectivityManager): Flow<Boolean> {
        return callbackFlow {
            with(connectivityManager) {
                val validNetworks: MutableSet<Network> = mutableSetOf()

                object : NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        getNetworkCapabilities(network)
                            ?.takeIf { it.hasCapability(NET_CAPABILITY_INTERNET) }
                            ?.let {
                                launch(IO) {
                                    try {
                                        Socket().use { it.connect(InetSocketAddress("8.8.8.8", 53), 1500) }

                                        validNetworks.apply { add(network); trySend(isNotEmpty()) }
                                    }
                                    catch (e: IOException) { trySend(validNetworks.isNotEmpty()) }
                                }
                            }
                    }

                    override fun onLost(network: Network) { validNetworks.apply { remove(network); trySend(isNotEmpty()) } }
                }.also { registerDefaultNetworkCallback(it); awaitClose { validNetworks.clear(); unregisterNetworkCallback(it) }}
            }
        }.stateIn(CoroutineScope(Default), WhileSubscribed(replayExpirationMillis = 0), false) }

    /**
     * Provides a [Resources] reference to any class who requires access to it functionalities but doesn't have a reference natively
     */
    @Singleton
    @Provides
    fun providesResources(@ApplicationContext context: Context): Resources = context.resources

}