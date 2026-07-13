package com.myperioddataismine

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.flexbox.FlexboxLayout
import java.text.DateFormat
import java.util.Calendar

class DayDataFragment : Fragment(R.layout.day_data_fragment) {
    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity = context as MainActivity
        val dayData = viewModel.getDayData()
        val previousIcon = view.findViewById<ImageView>(R.id.previous_day)
        val nextIcon = view.findViewById<ImageView>(R.id.next_day)
        val bleedingViewGroup = view.findViewById<ViewGroup>(R.id.bleeding)
        val bleedingIconsLayout = view.findViewById<FlexboxLayout>(R.id.bleeding_icons)
        val moodsViewGroup = view.findViewById<ViewGroup>(R.id.moods)
        val moodsIconsLayout = view.findViewById<FlexboxLayout>(R.id.moods_icons)
        val hornyViewGroup = view.findViewById<ViewGroup>(R.id.horny)
        val hornyIconsLayout = view.findViewById<FlexboxLayout>(R.id.horny_icons)
        val painsViewGroup = view.findViewById<ViewGroup>(R.id.pains)
        val painsIconsLayout = view.findViewById<FlexboxLayout>(R.id.pains_icons)
        val symptomsViewGroup = view.findViewById<ViewGroup>(R.id.symptoms)
        val symptomsLayout = view.findViewById<FlexboxLayout>(R.id.symptoms_icons)
        val sexViewGroup = view.findViewById<ViewGroup>(R.id.sex)
        val sexIconsLayout = view.findViewById<FlexboxLayout>(R.id.sex_icons)
        val contraceptionViewGroup = view.findViewById<ViewGroup>(R.id.contraception)
        val contraceptionIconsLayout = view.findViewById<FlexboxLayout>(R.id.contraception_icons)
        val notesViewGroup = view.findViewById<ViewGroup>(R.id.notes)
        val notesTextView = view.findViewById<TextView>(R.id.notes_text)

        setDate(view, dayData.date)
        setBleedingIcons(bleedingIconsLayout, dayData)
        setMoodsIcons(moodsIconsLayout, dayData)
        setHornyIcons(hornyIconsLayout, dayData)
        setPainsIcons(painsIconsLayout, dayData)
        setSymptomsIcons(symptomsLayout, dayData)
        setSexIcons(sexIconsLayout, dayData)
        setContraceptionIcons(contraceptionIconsLayout, dayData)
        notesTextView.text = dayData.notes

        previousIcon.setOnClickListener { mainActivity.viewPreviousDay() }
        if (dayData.isToday()) {
            nextIcon.setColorFilter(Color.LTGRAY)
        } else {
            nextIcon.setOnClickListener { mainActivity.viewNextDay() }
        }
        bleedingViewGroup.setOnClickListener {
            mainActivity.editBleeding()
        }
        moodsViewGroup.setOnClickListener {
            mainActivity.editMoods()
        }
        hornyViewGroup.setOnClickListener {
            mainActivity.editHorny()
        }
        painsViewGroup.setOnClickListener {
            mainActivity.editPains()
        }
        symptomsViewGroup.setOnClickListener {
            mainActivity.editSymptoms()
        }
        sexViewGroup.setOnClickListener {
            mainActivity.editSex()
        }
        contraceptionViewGroup.setOnClickListener {
            mainActivity.editContraception()
        }
        notesViewGroup.setOnClickListener {
            mainActivity.editNotes()
        }
    }

    private fun setDate(view: View, date: Calendar) {
        val dateTextView = view.findViewById<TextView>(R.id.date)
        val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM)
        val dateString = dateFormat.format(date.time)
        dateTextView.text = dateString
    }

    private fun setBleedingIcons(bleedingViewGroup: ViewGroup, dayData: DayData) {
        if (dayData.bleedingFlowLevel >= ImageData.flowLevel.size) {
            Log.e(TAG, "Bleeding flow level ${dayData.bleedingFlowLevel} is greater than ${ImageData.flowLevel.size - 1}")
        } else if (dayData.bleedingFlowLevel > 0) {
            addImage(bleedingViewGroup, ImageData.flowLevel[dayData.bleedingFlowLevel])
        }
        if (dayData.bleedingSmallClots > 0) {
            addImage(bleedingViewGroup, ImageData.smallClots)
        }
        if (dayData.bleedingBigClots > 0) {
            addImage(bleedingViewGroup, ImageData.bigClots)
        }
        if (bleedingViewGroup.isEmpty()) {
            addText(bleedingViewGroup, R.string.nothing)
        }
    }

    private fun setMoodsIcons(moodsViewGroup: ViewGroup, dayData: DayData) {
        for (moodIndex in ImageData.moods.indices) {
            if (((dayData.moods shr moodIndex) and 1) != 0) {
                addImage(moodsViewGroup, ImageData.moods[moodIndex])
            }
        }
        if (moodsViewGroup.isEmpty()) {
            addText(moodsViewGroup, R.string.nothing)
        }
    }

    private fun setHornyIcons(hornyViewGroup: ViewGroup, dayData: DayData) {
        if (dayData.horny > 0) {
            addImage(hornyViewGroup, ImageData.horny)
        }
        if (hornyViewGroup.isEmpty()) {
            addText(hornyViewGroup, R.string.nothing)
        }
    }

    private fun setPainsIcons(painsViewGroup: ViewGroup, dayData: DayData) {
        for (painIndex in ImageData.pains.indices) {
            if (((dayData.pains shr painIndex) and 1) != 0) {
                addImage(painsViewGroup, ImageData.pains[painIndex])
            }
        }
        if (painsViewGroup.isEmpty()) {
            addText(painsViewGroup, R.string.nothing)
        }
    }

    private fun setSymptomsIcons(symptomsViewGroup: ViewGroup, dayData: DayData) {
        for (symptomIndex in ImageData.symptoms.indices) {
            if (((dayData.symptoms shr symptomIndex) and 1) != 0) {
                addImage(symptomsViewGroup, ImageData.symptoms[symptomIndex])
            }
        }
        if (symptomsViewGroup.isEmpty()) {
            addText(symptomsViewGroup, R.string.nothing)
        }
    }

    private fun setSexIcons(sexViewGroup: ViewGroup, dayData: DayData) {
        for (sexActivitiesIndex in ImageData.sexActivities.indices) {
            if (((dayData.sexActivities shr sexActivitiesIndex) and 1) != 0) {
                addImage(sexViewGroup, ImageData.sexActivities[sexActivitiesIndex])
            }
        }
        for (sexProtectionsIndex in ImageData.sexProtections.indices) {
            if (((dayData.sexActivities shr sexProtectionsIndex) and 1) != 0) {
                addImage(sexViewGroup, ImageData.sexProtections[sexProtectionsIndex])
            }
        }
        if (sexViewGroup.isEmpty()) {
            addText(sexViewGroup, R.string.nothing)
        }
    }

    private fun setContraceptionIcons(contraceptionsViewGroup: ViewGroup, dayData: DayData) {
        if (dayData.contraceptionPill) {
            addImage(contraceptionsViewGroup, ImageData.contraceptionPill)
        }
        if (dayData.contraceptionPatches > 0) {
            addImage(contraceptionsViewGroup, ImageData.contraceptionPatches)
        }
        if (dayData.contraceptionIUD) {
            addImage(contraceptionsViewGroup, ImageData.contraceptionIUD)
        }
        if (dayData.contraceptionRing) {
            addImage(contraceptionsViewGroup, ImageData.contraceptionRing)
        }
        if (dayData.contraceptionImplant) {
            addImage(contraceptionsViewGroup, ImageData.contraceptionImplant)
        }
        if (dayData.contraceptionShot) {
            addImage(contraceptionsViewGroup, ImageData.contraceptionShot)
        }
        if (contraceptionsViewGroup.isEmpty()) {
            addText(contraceptionsViewGroup, R.string.nothing)
        }
    }

    private fun addText(viewGroup: ViewGroup, stringID: Int) {
        val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val textLayout = inflater.inflate(R.layout.text_view, viewGroup, false)
        val textView = textLayout.findViewById<TextView>(R.id.text_view)
        textView.text = getString(stringID)
        viewGroup.addView(textView)
    }

    private fun addImage(viewGroup: ViewGroup, imageData: Pair<Int, Int>) {
        val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val imageLayout = inflater.inflate(R.layout.image_view, viewGroup, false)
        val imageView = imageLayout.findViewById<ImageView>(R.id.image_view)
        imageView.setContentDescription(getString(imageData.first))
        imageView.setImageResource(imageData.second)
        viewGroup.addView(imageView)
    }

    companion object {
        private const val TAG = "DayDataFragment"
    }
}
