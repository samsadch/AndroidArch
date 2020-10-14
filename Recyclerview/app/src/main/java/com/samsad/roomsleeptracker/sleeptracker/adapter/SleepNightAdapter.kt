package com.samsad.roomsleeptracker.sleeptracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.samsad.roomsleeptracker.R
import com.samsad.roomsleeptracker.convertDurationToFormatted
import com.samsad.roomsleeptracker.convertNumericQualityToString
import com.samsad.roomsleeptracker.database.SleepNight

class SleepNightAdapter : RecyclerView.Adapter<SleepNightAdapter.ViewHolder>() {

    var data = listOf<SleepNight>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val view = inflator.inflate(R.layout.list_item_sleep_tracker, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        val res = holder.itemView.context.resources
        holder.sleepLength.text =
            convertDurationToFormatted(item.startTimeInMilli, item.endTimeInMilli, res)
        holder.sleepQuality.text = convertNumericQualityToString(item.sleepQuality, res)
        holder.quality_image.setImageResource(
            when (item.sleepQuality) {
                0 -> R.drawable.ic_sleep_0
                1 -> R.drawable.ic_sleep_1
                2 -> R.drawable.ic_sleep_2
                3 -> R.drawable.ic_sleep_3
                4 -> R.drawable.ic_sleep_4
                5 -> R.drawable.ic_sleep_5
                else -> R.drawable.ic_sleep_active
            }
        )
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sleepQuality: TextView = itemView.findViewById(R.id.sleep_quality_text)
        val sleepLength: TextView = itemView.findViewById(R.id.sleep_length_text)
        val quality_image: ImageView = itemView.findViewById(R.id.quality_image)
    }

}