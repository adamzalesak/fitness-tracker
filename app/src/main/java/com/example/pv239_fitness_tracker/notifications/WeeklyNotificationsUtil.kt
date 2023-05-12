package com.example.pv239_fitness_tracker.notifications

import android.content.Context
import android.content.SharedPreferences
import java.time.DayOfWeek

object WeeklyNotificationsUtil {
    fun setupNotificationsByPreferences(context: Context, sharedPreferences: SharedPreferences) {
        val notificationsWeekly = sharedPreferences.getBoolean("notifications_weekly", false)
        val notificationDayString = sharedPreferences.getString("notifications_day", "7")
        val notificationDay = DayOfWeek.of(notificationDayString?.toInt() ?: 7)
        val notificationHourString = sharedPreferences.getString("notification_hour", "18")
        val notificationHour = notificationHourString?.toInt() ?: 18

        if (notificationsWeekly) {
            WeeklyNotificationsManager.startNotifications(
                context, notificationDay, notificationHour
            )
        } else {
            WeeklyNotificationsManager.stopNotifications(context)
        }
    }
}
