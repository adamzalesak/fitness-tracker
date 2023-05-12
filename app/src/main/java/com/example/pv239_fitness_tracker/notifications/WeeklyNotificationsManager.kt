package com.example.pv239_fitness_tracker.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.time.DayOfWeek
import java.util.Calendar
import java.util.TimeZone

object WeeklyNotificationsManager {
    private const val NOTIFICATION_REQUEST_CODE = 1

    fun startNotifications(
        context: Context,
        dayOfWeek: DayOfWeek,
        hour: Int,
    ) {

        stopNotifications(context)

        val calendar = Calendar.getInstance(TimeZone.getDefault()).apply {
            // SUNDAY = 1 in Calendar, but 0 in DayOfWeek so we need to add 1
            set(Calendar.DAY_OF_WEEK, dayOfWeek.value + 1)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        if (calendar.timeInMillis < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 7)
        }

        val notificationTime = calendar.timeInMillis

        val intent = Intent(context, WeeklyNotificationsAlarmReceiver::class.java)
        intent.putExtra("dayOfWeek", dayOfWeek.value)
        intent.putExtra("hour", hour)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            NOTIFICATION_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP, notificationTime, pendingIntent
        )
    }

    fun stopNotifications(
        context: Context,
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, WeeklyNotificationsAlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(
                context, NOTIFICATION_REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE
            )
        }
        alarmManager.cancel(intent)
    }
}
