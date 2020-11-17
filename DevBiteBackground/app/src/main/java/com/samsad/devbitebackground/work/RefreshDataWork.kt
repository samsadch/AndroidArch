package com.samsad.devbitebackground.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bumptech.glide.load.HttpException
import com.samsad.devbitebackground.database.getDatabase
import com.samsad.devbitebackground.repository.VideosRepository


class RefreshDataWork(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    //Our worker will run until do work is returns the result
    //eg:acess network, write to DB and Workmanger make sure
    //the OS doesn't interrupt our work.

    //Do work will run in background thread
    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = VideosRepository(database)

        repository.refreshVideos()

        return try {
            Result.success()
        } catch (e: HttpException) {
            Result.failure()
        }
    }

}