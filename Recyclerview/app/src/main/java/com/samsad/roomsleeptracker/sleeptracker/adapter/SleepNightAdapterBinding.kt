package com.samsad.roomsleeptracker.sleeptracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.samsad.roomsleeptracker.database.SleepNight
import com.samsad.roomsleeptracker.databinding.ListItemSleepGridBinding

class SleepNightAdapterBinding(val clickListener: SleepNightListener) :
    ListAdapter<SleepNight, SleepNightAdapterBinding.ViewHolder>(
        SleepNightDiffCallback2()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class ViewHolder private constructor(val binding: ListItemSleepGridBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SleepNight, clickListener: SleepNightListener) {
            val res = itemView.context.resources
            binding.sleep = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflator = LayoutInflater.from(parent.context)
                val binding = ListItemSleepGridBinding.inflate(inflator, parent, false)
                return ViewHolder(
                    binding
                )
            }
        }
    }
}

class SleepNightDiffCallback2 : DiffUtil.ItemCallback<SleepNight>() {

    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem.nightId == newItem.nightId
    }

    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem == newItem
    }

}

class SleepNightListener(val clickListener: (sleepId: Long) -> Unit) {
    fun onClick(night: SleepNight) = clickListener(night.nightId)
}

