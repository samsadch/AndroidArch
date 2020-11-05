package com.samsad.kotlinmarsnetwork.detail

import android.app.Application
import androidx.lifecycle.*
import com.samsad.kotlinmarsnetwork.R
import com.samsad.kotlinmarsnetwork.network.MarsProperty

/**
 * The [ViewModel] that is associated with the [DetailFragment].
 */
class DetailViewModel(marsProperty: MarsProperty, app: Application) :
    AndroidViewModel(app) {

    private val _selectedProperty = MutableLiveData<MarsProperty>()
    val selectedProperty: LiveData<MarsProperty>
        get() = _selectedProperty

    init {
        _selectedProperty.value = marsProperty
    }

    val displayPropertyPrice = Transformations.map(selectedProperty) {
        app.applicationContext.getString(
            when (it.isRental) {
                true -> R.string.display_price_monthly_rental
                else -> R.string.display_price
            }, it.price
        )
    }

    val displayPropertyType = Transformations.map(selectedProperty) {
        app.applicationContext.getString(
            R.string.display_type, app.applicationContext.getString(
                when (it.isRental) {
                    true -> R.string.type_rent
                    else -> R.string.type_sale
                }
            )
        )
    }

}
