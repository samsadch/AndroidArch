package com.samsad.devbitebackground.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.samsad.devbitebackground.database.getDatabase
import com.samsad.devbitebackground.network.asDomainModel
import com.samsad.devbitebackground.domain.Video
import com.samsad.devbitebackground.network.Network
import com.samsad.devbitebackground.repository.VideosRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * DevByteViewModel designed to store and manage UI-related data in a lifecycle conscious way. This
 * allows data to survive configuration changes such as screen rotations. In addition, background
 * work such as fetching network results can continue through configuration changes and deliver
 * results after the new Fragment or Activity is available.
 *
 * @param application The application that this [DevByteViewModel] is attached to, it's safe to hold a
 * reference to applications across rotation since Application is never recreated during actiivty
 * or fragment lifecycle events.
 */
class DevByteViewModel(application: Application) : AndroidViewModel(application) {

    private val vieModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(vieModelJob+Dispatchers.Main)

    private val database = getDatabase(application)
    private val videoRepository =  VideosRepository(database)

    init {
        viewModelScope.launch {
            videoRepository.refreshVideos()
        }
    }

    val playlist = videoRepository.videos

    /**
     * Factory for constructing DevByteViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DevByteViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DevByteViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

    override fun onCleared() {
        super.onCleared()

    }
}
