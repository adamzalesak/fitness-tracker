package com.example.pv239_fitness_tracker.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.preference.PreferenceManager

class BootCompletedBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

                WeeklyNotificationsUtil.setupNotificationsByPreferences(context, sharedPreferences)
            }
        }
    }
}
