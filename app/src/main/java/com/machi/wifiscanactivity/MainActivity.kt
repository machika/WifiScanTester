package com.machi.wifiscanactivity

import android.Manifest
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    val JOB_ID = 1;
    val REQUEST_PERM_LOCATION = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED) {
            val permArray : Array<String> = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION);
            requestPermissions(permArray, REQUEST_PERM_LOCATION)
        } else {
            scheduleJob()
        }
    }

    fun scheduleJob() {
        val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

        val jobInfo = JobInfo.Builder(JOB_ID, ComponentName(this, WifiScanJobService::class.java)).apply {
            setMinimumLatency(5000)
        }.build()

        scheduler.schedule(jobInfo)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_PERM_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            scheduleJob();
            return;
        }
        finish()
    }
}
