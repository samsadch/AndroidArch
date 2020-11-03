package com.samsad.kotlinmarsnetwork.network

import com.bosphere.filelogger.FL
import com.samsad.kotlinmarsnetwork.MainActivity.Companion.TAG
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://mars.udacity.com/"

/**In order for Moshi's annotations work properly with Kotlin
 * we need to add kotlin Json adapter factory*/
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/**
 *  Interface that explains how retrofit talk to Webserver
 *  using HTTP requests. Retrofit will create an object that implements
 *  our interface with all of the methods that talk to the server
 * */
interface MarsApiService {
    /**Call object is used to start the request, to create a retrofit service you call
     * retrofit.create() passing service interface API*/
    @GET("realestate")
    fun getProperties(): Call<List<MarsProperty>>
}

/** Since retrofit create call is expensive our app only need one retrofit service
 * instance we expose retrofit object to rest of the application using public Object
 * */
object MarsAPI {
    val retrofitService: MarsApiService by lazy {
        FL.d(TAG, " Called for retrofit Service")
        retrofit.create(MarsApiService::class.java)
    }
}