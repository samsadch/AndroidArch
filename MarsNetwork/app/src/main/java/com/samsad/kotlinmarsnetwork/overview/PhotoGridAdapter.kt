package com.samsad.kotlinmarsnetwork.overview

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.samsad.kotlinmarsnetwork.R
import com.samsad.kotlinmarsnetwork.network.MarsProperty

class PhotoGridAdapter :ListAdapter<MarsProperty,PhotoGridAdapter.MarsPropertyViewHolder>(DiffCallback){
    
}