package com.samsad.kotlinmarsnetwork

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bosphere.filelogger.FL


/**
 * [AppCompatActivity] contains the navigation fragment
 * */
class MainActivity : AppCompatActivity() {
    companion object{
        const val TAG = "TAG : "
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        FL.d("RESUME :"+ "Main activity")
    }
}