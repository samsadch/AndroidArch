package com.samsad.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.samsad.navigation.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        navController = this.findNavController(R.id.navhost_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
        //return super.onSupportNavigateUp()
    }
}