package com.samsad.kotlinmarsnetwork.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samsad.kotlinmarsnetwork.network.MarsAPI
import com.samsad.kotlinmarsnetwork.network.MarsProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.await

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 *
 */
class OverviewViewModel : ViewModel() {


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the request status String
    val status: LiveData<String>
        get() = _status

    private val _property = MutableLiveData<MarsProperty>()

    val property: LiveData<MarsProperty>
        get() = _property

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getMarsRealEstateProperties()
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
    private fun getMarsRealEstateProperties() {
        coroutineScope.launch {
            var getPropertiesDeferred = MarsAPI.retrofitService.getProperties()
            try {
                var listResult = getPropertiesDeferred.await()
                if (listResult.isNotEmpty()) {
                    _property.value = listResult[0]
                }
                _status.value = "Success: ${listResult.size} Mars properties retrieved"
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
        _status.value = "Set the Mars API Response here!"
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
