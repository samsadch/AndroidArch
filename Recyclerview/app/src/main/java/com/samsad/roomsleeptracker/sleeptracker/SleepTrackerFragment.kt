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
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.samsad.roomsleeptracker.R
import com.samsad.roomsleeptracker.database.SleepDatabase
import com.samsad.roomsleeptracker.databinding.SleepTrackerFragmentBinding
import com.samsad.roomsleeptracker.sleeptracker.adapter.SleepNightAdapterBinding
import com.samsad.roomsleeptracker.sleeptracker.adapter.SleepNightListener

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

        val layoutManager = GridLayoutManager(activity, 3)

        binding.lifecycleOwner = this

        val adapter = SleepNightAdapterBinding(SleepNightListener { nightId ->
            sleepTrackerViewModel.onSleepItemClicked(nightId)
        })
        binding.sleepRcv.adapter = adapter
        binding.sleepRcv.layoutManager = layoutManager
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (position) {
                    0 -> 3
                    else -> 1
                }
            }

        }
        sleepTrackerViewModel.nights.observe(viewLifecycleOwner, Observer {
            it?.let {
                //adapter.data = it
                adapter.addHeaderAndSubmitList(it)
            }
        })

        sleepTrackerViewModel.navigateToDetail.observe(viewLifecycleOwner, Observer { night ->
            night?.let {
                this.findNavController().navigate(
                    SleepTrackerFragmentDirections
                        .actionSleepTrackerFragmentToDetailFragment(it)
                )
                sleepTrackerViewModel.onSleepItemDetailNavigated()
            }

        })

        sleepTrackerViewModel.navigateToSleepQuality.observe(viewLifecycleOwner, Observer { night ->
            night?.let {
                this.findNavController().navigate(
                    SleepTrackerFragmentDirections
                        .actionSleepTrackerFragmentToSleepQualityFragment(night.nightId)
                )
                sleepTrackerViewModel.doneNavigating()
            }
        })

        sleepTrackerViewModel.showSnackBarEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    getString(R.string.cleared_message),
                    Snackbar.LENGTH_SHORT
                ).show()
                sleepTrackerViewModel.doneShowingSnackBar()
            }
        })



        return binding.root
    }

}