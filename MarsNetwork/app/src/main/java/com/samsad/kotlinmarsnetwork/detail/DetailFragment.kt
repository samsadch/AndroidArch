package com.samsad.kotlinmarsnetwork.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelProviders.*
import com.samsad.kotlinmarsnetwork.databinding.FragmentDetailBinding
import java.util.EnumSet.of
import java.util.List.of

/**
 * This [Fragment] will show the detailed information about a selected piece of Mars real estate.
 */
class DetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        @Suppress("UNUSED_VARIABLE")
        val application = requireNotNull(activity).application
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val marsProperty = DetailFragmentArgs.fromBundle(requireArguments()).selectedProperty
        val viemodelFactory = DetailViewModelFactory(marsProperty, application)
        binding.viewModel =
            ViewModelProviders.of(this, viemodelFactory).get(DetailViewModel::class.java)
        return binding.root
    }
}
