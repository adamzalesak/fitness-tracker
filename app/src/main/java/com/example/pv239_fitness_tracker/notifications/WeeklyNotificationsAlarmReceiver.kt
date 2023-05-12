package com.example.pv239_fitness_tracker.notifications

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.example.pv239_fitness_tracker.MainActivity
import com.example.pv239_fitness_tracker.R
import com.example.pv239_fitness_tracker.repository.ExerciseRepository
import java.time.DayOfWeek


class WeeklyNotificationsAlarmReceiver : BroadcastReceiver() {
    private lateinit var exerciseRepository: ExerciseRepository

    override fun onReceive(context: Context, intent: Intent) {
        exerciseRepository = ExerciseRepository(context)

        val notificationDayOfWeekInt = intent.getIntExtra("dayOfWeek", 1)
        val notificationDayOfWeek = DayOfWeek.of(notificationDayOfWeekInt)
        val notificationHour = intent.getIntExtra("hour", 18)

        notify(context)

        WeeklyNotificationsManager.startNotifications(
            context,
            notificationDayOfWeek,
            notificationHour
        )
    }

    private fun notify(context: Context) {
        val channelId = "weekly_notifications"
        val description = "Weekly notifications"

        val mostImprovedExercise = exerciseRepository.getMostImprovedExercise()
        val numberOfActivitiesInLastWeek = exerciseRepository.getNumberOfActivitiesInLastWeek()


        val notificationChannel =
            NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
        notificationChannel.enableLights(true)
        notificationChannel.enableVibration(false)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.createNotificationChannel(notificationChannel)


        val notificationBuilder = Notification.Builder(context, channelId)
            .setSmallIcon(R.drawable.round_fitness_center_24)

        val startActivityIntent = NavDeepLinkBuilder(context)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.calendar_fragment)
            .createPendingIntent()

        if (mostImprovedExercise != null) {
            val exercise = exerciseRepository.getExerciseById(mostImprovedExercise.id)

            val argumentsBundle = Bundle()
            argumentsBundle.putParcelable("selectedExercise", exercise)

            val statisticsIntent = NavDeepLinkBuilder(context)
                .setComponentName(MainActivity::class.java)
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.statisticsFragment)
                .setArguments(argumentsBundle)
                .createPendingIntent()

            notificationBuilder
                .setContentTitle(context.getString(R.string.notification_improvement_title))
                .setContentText(
                    context.getString(
                        R.string.notification_improvement_message,
                        mostImprovedExercise.name
                    )
                )
                .setContentIntent(statisticsIntent)
                .setActions(
                    // see statistics fragment
                    Notification.Action.Builder(
                        null,
                        context.getString(R.string.notification_improvement_action),
                        statisticsIntent
                    ).build()
                )
        } else if (numberOfActivitiesInLastWeek > 0) {
            notificationBuilder
                .setContentTitle(context.getString(R.string.notification_progress_title))
                .setContentText(
                    context.getString(
                        R.string.notification_progress_message,
                        numberOfActivitiesInLastWeek
                    )
                )
                .setContentIntent(startActivityIntent)
                .setActions(
                    Notification.Action.Builder(
                        null,
                        context.getString(R.string.notification_progress_action),
                        startActivityIntent
                    ).build()
                )
        } else {
            notificationBuilder
                .setContentTitle(context.getString(R.string.notification_no_progress_title))
                .setContentText(context.getString(R.string.notification_no_progress_message))
                .setContentIntent(startActivityIntent)
                .setActions(
                    Notification.Action.Builder(
                        null,
                        context.getString(R.string.notification_no_progress_action),
                        startActivityIntent
                    ).build()
                )
        }

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            notificationManager.notify(1, notificationBuilder.build())
        }
    }
}
