package com.samsad.roomsleeptracker.ui.sleeptracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.samsad.roomsleeptracker.database.SleepDatabaseDao


class SleepTrackerViewModel(
    val database: SleepDatabaseDao,
    application: Application
) : AndroidViewModel(application) {
}
