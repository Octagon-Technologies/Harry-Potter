package com.octagontechnologies.harrypotter.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network

class NetworkMoniter(private val context: Context) {

    private val connectivityManager by lazy {
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
    private var networkCallback: NetworkCallback? = null

    /**
     * Returns an existing network callback or creates an new one if there is no existing network callback.
     *
     * This is important since we need to register and unregister the SAME network callback
     * POV: Not me using synchronized() without knowing it's true function... All I know is that it is IMPORTANT
     *      in Room DB creation :)
     */
    private fun getNetworkCallback(onNetworkChange: (Boolean) -> Unit): NetworkCallback =
        synchronized(Unit) {
            if (networkCallback == null) {
                networkCallback = object : NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        super.onAvailable(network)
                        onNetworkChange(true)
                    }
                }
            }

            return networkCallback!!
        }

    /**
     * Registers a listener on network changes
     *
     * @param onNetworkChange -> Receives true if network is turned ON.... (we don't care if it's off since we want
     *                           to retry the network call only if we know we have network connection)
     */
    fun registerNetworkListener(onNetworkChange: (Boolean) -> Unit) {
        connectivityManager.registerDefaultNetworkCallback(
            getNetworkCallback(onNetworkChange)
        )
    }

    /**
     * Unregisters the network listener
     */
    fun unregisterNetworkListener() {
        networkCallback?.let { connectivityManager.unregisterNetworkCallback(it) }
    }
}