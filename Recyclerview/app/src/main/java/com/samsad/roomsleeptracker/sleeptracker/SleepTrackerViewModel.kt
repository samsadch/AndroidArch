package com.samsad.roomsleeptracker.sleeptracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.samsad.roomsleeptracker.database.SleepDatabaseDao
import com.samsad.roomsleeptracker.database.SleepNight
import com.samsad.roomsleeptracker.formatNights
import kotlinx.coroutines.*


class SleepTrackerViewModel(
    val database: SleepDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    //This viewmodel job allows you to Cancel all coroutines started by coroutine when
    //Viewmodel is no longer used and Destroyed
    private var viewModelJob = Job()


    //Scope determines what thread the courotine will run on and
    //also need to know about Job
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    private var toNight = MutableLiveData<SleepNight?>()

    val nights = database.getAllNights()

    private val _navigateToSleepQuality = MutableLiveData<SleepNight>()

    val navigateToSleepQuality: LiveData<SleepNight>
        get() = _navigateToSleepQuality

    fun doneNavigating() {
        _navigateToSleepQuality.value = null
    }

    val nightString = Transformations.map(nights) {
        formatNights(it, application.resources)
    }

    val startButtonVisible = Transformations.map(toNight) {
        null == it
    }

    val stopButtonVisible = Transformations.map(toNight) {
        null != it
    }

    val clearButtonVisible = Transformations.map(nights) {
        it?.isNotEmpty()
    }

    private var _showSnackBarEvent = MutableLiveData<Boolean>()
    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackBarEvent

    fun doneShowingSnackBar() {
        _showSnackBarEvent.value = false
    }

    init {
        initializeTonight()
    }

    private fun initializeTonight() {
        //In the ui scope we launch a coroutine
        //Launching coroutine creates a the coroutine without blocking the current thread into context defined by the scope.
        uiScope.launch {
            toNight.value = getTonightFromDataBase()
        }
    }

    private suspend fun getTonightFromDataBase(): SleepNight? {
        return withContext(Dispatchers.IO) {
            var night = database.getTonight()
            if (night?.startTimeInMilli != night?.endTimeInMilli) {
                night = null
            }
            night
        }
    }

    fun onStartTracking() {
        uiScope.launch {
            val night = SleepNight()
            insert(night)
            toNight.value = getTonightFromDataBase()
        }
    }

    private suspend fun insert(night: SleepNight) {
        withContext(Dispatchers.IO) {
            database.insert(night)
        }
    }

    fun onStopTracking() {
        uiScope.launch {
            val oldNight = toNight.value ?: return@launch
            oldNight.endTimeInMilli = System.currentTimeMillis()
            update(oldNight)
            _navigateToSleepQuality.value = oldNight
        }
    }

    private suspend fun update(oldNight: SleepNight) {
        withContext(Dispatchers.IO) {
            database.update(oldNight)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onClearPress() {
        uiScope.launch {
            clear()
            toNight.value = null
            _showSnackBarEvent.value = true
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    private val _navigateToDetail = MutableLiveData<Long>()

    val navigateToDetail: LiveData<Long>
        get() = _navigateToDetail

    fun onSleepItemClicked(id: Long) {
        _navigateToDetail.value = id
    }

    fun onSleepItemDetailNavigated() {
        _navigateToDetail.value = null
    }

}
