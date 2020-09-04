package com.samsad.colormyviews

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setListners()
    }

    private fun setListners() {
        var clickableViews: List<View> = listOf(
            box_one_text,
            box_two_text,
            box_three_text,
            box_four_text,
            box_five_text,
            constrained_layout,
            red_button,
            yellow_button,
            green_button
        )
        for (item in clickableViews) {
            item.setOnClickListener {
                makeColored(it)
            }
        }
    }

    private fun makeColored(it: View) {
        when (it.id) {
            //Boxes using Color class for Colouring Background
            R.id.box_one_text -> it.setBackgroundColor(Color.DKGRAY)
            R.id.box_two_text -> it.setBackgroundColor(Color.GRAY)


            R.id.box_three_text -> it.setBackgroundResource(android.R.color.holo_green_light)
            R.id.box_four_text -> it.setBackgroundResource(android.R.color.holo_green_dark)
            R.id.box_five_text -> it.setBackgroundResource(android.R.color.holo_green_light)

            R.id.green_button -> it.setBackgroundResource(R.color.my_green)
            R.id.red_button -> it.setBackgroundResource(R.color.my_red)
            R.id.yellow_button -> it.setBackgroundResource(R.color.my_yellow)

            else -> it.setBackgroundColor(Color.LTGRAY)
        }


    }
}