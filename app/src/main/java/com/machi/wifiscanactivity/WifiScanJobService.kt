package com.machi.wifiscanactivity

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.util.Log

class WifiScanJobService : JobService() {

    val LOG_TAG = "WifiScanJobService"

    override fun onStartJob(params: JobParameters?): Boolean {
        val wifiManager = getSystemService(Context.WIFI_SERVICE) as WifiManager
        Log.d(LOG_TAG, "job started")

        //With permission
        var wifiInfo = wifiManager.getConnectionInfo()
        Log.d(LOG_TAG, "With permission: getConnectinoInfo(): BSSID = " + wifiInfo.bssid)

        var scanResult = wifiManager.getScanResults()
        if (scanResult.size == 0) {
            Log.d(LOG_TAG, "With permission: no result in getScanResults()")
        }
        for (result in scanResult) {
            Log.d(LOG_TAG, "With permission: getScanResults(): SSID = " + result.SSID + ", capability: " + result.capabilities)
        }

        //Without permission
        var configurationList = wifiManager.getConfiguredNetworks()
        if (configurationList.size == 0 ) {
            Log.d(LOG_TAG, "Without permission: no result in getConfiguredNetworks()")
        }
        for (config in configurationList) {
            if (config.status == WifiConfiguration.Status.CURRENT) {
                Log.d(LOG_TAG, "Without permission: configured network: WPA_PSK:" + config.allowedKeyManagement.get(WifiConfiguration.KeyMgmt.WPA_PSK))
            }
        }

        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        return true
    }

}
