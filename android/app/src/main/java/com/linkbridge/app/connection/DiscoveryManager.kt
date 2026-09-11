package com.linkbridge.app.connection

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.util.Log

class DiscoveryManager(private val context: Context) {
    private val nsdManager: NsdManager = context.getSystemService(Context.NSD_SERVICE) as NsdManager
    private val serviceType = "_linkbridge._tcp."
    private val serviceName = "LinkBridge_Android"

    private val registrationListener = object : NsdManager.RegistrationListener {
        override fun onServiceRegistered(NsdServiceInfo: NsdServiceInfo) {
            Log.d("LinkBridge", "Service registered: ${NsdServiceInfo.serviceName}")
        }
        override fun onRegistrationFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
            Log.e("LinkBridge", "Registration failed: $errorCode")
        }
        override fun onServiceUnregistered(arg0: NsdServiceInfo) {}
        override fun onUnregistrationFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {}
    }

    fun startAdvertising(port: Int) {
        val serviceInfo = NsdServiceInfo().apply {
            this.serviceName = this@DiscoveryManager.serviceName
            this.serviceType = this@DiscoveryManager.serviceType
            this.port = port
        }
        nsdManager.registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, registrationListener)
    }

    fun stopAdvertising() {
        try {
            nsdManager.unregisterService(registrationListener)
        } catch (e: Exception) {
            Log.e("LinkBridge", "Error unregistering service", e)
        }
    }
}
