package com.samsad.roomsleeptracker.sleeptracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.samsad.roomsleeptracker.R
import com.samsad.roomsleeptracker.database.SleepDatabase
import com.samsad.roomsleeptracker.databinding.SleepTrackerFragmentBinding

class SleepTrackerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: SleepTrackerFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.sleep_tracker_fragment, container, false
        )

        val application = requireNotNull(this.activity).application

        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao

        val viewModelFactory =
            SleepTrackerViewModelFactory(application = application, dataSource = dataSource)

        val sleepTrackerViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SleepTrackerViewModel::class.java)

        binding.sleepTrackerViewModel = sleepTrackerViewModel

        binding.lifecycleOwner = this

        sleepTrackerViewModel.navigateToSleepQuality.observe(viewLifecycleOwner, Observer { night ->

            val action =
                SleepTrackerFragmentDirections.actionSleepTrackerFragmentToSleepQualityFragment(
                    night.nightId
                )
            this.findNavController().navigate(action)
            sleepTrackerViewModel.doneNavigating()
        })



        return binding.root
    }

}