package com.samsad.roomsleeptracker.ui.sleepquality

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.samsad.roomsleeptracker.R
import com.samsad.roomsleeptracker.databinding.SleepQualityFragmentBinding

class SleepQualityFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Get a reference to the binding object and inflate the fragment views.
        val binding: SleepQualityFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.sleep_quality_fragment, container, false
        )

        val application = requireNotNull(this.activity).application

        return binding.root
    }


}