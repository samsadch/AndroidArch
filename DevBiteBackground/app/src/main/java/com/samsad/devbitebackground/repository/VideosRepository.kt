package com.samsad.devbitebackground.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.samsad.devbitebackground.database.VideoDatabase
import com.samsad.devbitebackground.database.asDomainModel
import com.samsad.devbitebackground.domain.Video
import com.samsad.devbitebackground.network.Network
import com.samsad.devbitebackground.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * Repository for fetching devbyte videos from the network and storing on disk.*/
class VideosRepository(private val database: VideoDatabase) {

    val videos: LiveData<List<Video>> = Transformations.map(database.videoDao.getVideos()){
        it.asDomainModel()
    }

    suspend fun refreshVideos() {
        //withContext forces kotlin Coroutine to switch to dispatcher specified
        withContext(Dispatchers.IO) {
            //Await fn tell the coroutine to sususpend until its available
            val playlist = Network.devbytes.getPlaylist().await()
            database.videoDao.insertAll(*playlist.asDatabaseModel())
        }
    }
}