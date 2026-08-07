package com.komizen.az.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.komizen.az.worker.SyncWorker

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            SyncWorker.schedulePeriodic(context)
        }
    }
}
