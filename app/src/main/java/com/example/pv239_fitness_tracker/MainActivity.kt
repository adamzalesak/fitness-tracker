package com.example.pv239_fitness_tracker

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.example.pv239_fitness_tracker.databinding.ActivityMainBinding
import com.example.pv239_fitness_tracker.notifications.WeeklyNotificationsUtil


class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val PERMISSION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        setupAppTheme(sharedPreferences)

        setupNavigation()

        WeeklyNotificationsUtil.setupNotificationsByPreferences(this, sharedPreferences)

        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(this)


    }

    private fun setupAppTheme(sharedPreferences: SharedPreferences) {
        when (sharedPreferences.getString("theme", "system")) {
            "system" -> {
                delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }

            "light" -> {
                delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO
            }

            "dark" -> {
                delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES
            }
        }
    }

    private fun setupNavigation() {
        setSupportActionBar(findViewById(R.id.toolbar))

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)

        val fragmentsWithNavigation = listOf(
            R.id.calendar_fragment,
            R.id.exercises_fragment,
            R.id.settings_fragment,
            R.id.statisticsFragment,
        )

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigation.isVisible = destination.id in fragmentsWithNavigation
            binding.toolbar.isVisible = destination.id !in fragmentsWithNavigation
            binding.toolbar.setNavigationOnClickListener { navController.navigateUp() }
        }

    }

    fun setToolbarTitle(title: String) {
        binding.toolbar.title = title
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        when (key) {
            "notifications_weekly", "notifications_day", "notification_hour" -> {
                val notificationsWeekly =
                    sharedPreferences.getBoolean("notifications_weekly", false)

                if (notificationsWeekly && ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.POST_NOTIFICATIONS,
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    sharedPreferences.edit().putBoolean("notifications_weekly", false).apply()
                    recreate()

                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                        PERMISSION_REQUEST_CODE,
                    )

                    return
                }

                WeeklyNotificationsUtil.setupNotificationsByPreferences(this, sharedPreferences)
            }

            "theme" -> {
                setupAppTheme(sharedPreferences)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        PreferenceManager.getDefaultSharedPreferences(this)
            .unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(applicationContext, "Permission granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }
}