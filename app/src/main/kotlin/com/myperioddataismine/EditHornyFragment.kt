package com.myperioddataismine

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.slider.Slider

class EditHornyFragment: EditSectionFragment(R.layout.edit_horny_fragment) {
    private lateinit var hornySlider: Slider

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dayData = viewModel.getDayData()
        val horny = cachedData?.getInt(HORNY)
            ?: dayData.horny

        initializeHornySlider(view, horny)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(HORNY, hornySlider.value.toInt())
    }

    fun initializeHornySlider(view: View, horny: Int) {
        val hornySliderInclude = view.findViewById<LinearLayout>(R.id.horny_slider)
        val hornyHeading = hornySliderInclude.findViewById<TextView>(R.id.slider_heading)
        val hornyImage = hornySliderInclude.findViewById<ImageView>(R.id.slider_image)
        hornyHeading.text = getString(R.string.horny)
        hornyImage.setImageResource(R.drawable.horny_placeholder)
        hornySlider = hornySliderInclude.findViewById(R.id.slider)
        hornySlider.valueFrom = -1.0f
        hornySlider.valueTo = 11.0f
        hornySlider.stepSize = 1.0f
        hornySlider.value = horny.toFloat()
    }

    override fun save() {
        val dayData = viewModel.getDayData()
        dayData.horny = hornySlider.value.toInt()
        super.save()
    }

    companion object {
        const val HORNY = "Horny"
    }
}
