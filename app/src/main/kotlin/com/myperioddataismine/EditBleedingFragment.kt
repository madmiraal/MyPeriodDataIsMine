package com.myperioddataismine

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import com.google.android.material.slider.Slider
import com.myperioddataismine.EntryValue.EntryType

class EditBleedingFragment: EditSectionFragment(R.layout.edit_bleeding_fragment) {
    private lateinit var flowLevelEntryAdapter: EntryAdapter
    private lateinit var smallClotsSlider: Slider
    private lateinit var bigClotsSlider: Slider
    private lateinit var excludeCheckBox: CheckBox
    private lateinit var notesEditText: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dayData = viewModel.getDayData()
        val cachedData = getCachedData(savedInstanceState)
        val flowLevel = cachedData?.getInt(FLOW_LEVEL)
            ?: dayData.bleedingFlowLevel
        val smallClots = cachedData?.getInt(SMALL_CLOTS)
            ?: dayData.bleedingSmallClots
        val bigClots = cachedData?.getInt(BIG_CLOTS)
            ?: dayData.bleedingBigClots
        val exclude = cachedData?.getBoolean(EXCLUDE)
            ?: dayData.bleedingExclude
        val notes = cachedData?.getString(NOTES)
            ?: dayData.bleedingNotes

        initializeFlowLevelEntryAdapter(view, flowLevel)
        initializeSmallClotsSlider(view, smallClots)
        initializeBigClotsSlider(view, bigClots)
        initializeExcludeCheckBox(view, exclude)
        initializeNotesEditText(view, notes)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(FLOW_LEVEL, flowLevelEntryAdapter.value)
        outState.putInt(SMALL_CLOTS, smallClotsSlider.value.toInt())
        outState.putInt(BIG_CLOTS, bigClotsSlider.value.toInt())
        outState.putBoolean(EXCLUDE, excludeCheckBox.isChecked)
        outState.putString(NOTES, notesEditText.text.toString())
    }

    fun initializeFlowLevelEntryAdapter(view: View, flowLevel: Int) {
        val flowLevelListInclude = view.findViewById<LinearLayout>(R.id.flow_level_list)
        val flowLevelHeading = flowLevelListInclude.findViewById<TextView>(R.id.list_heading)
        val flowLevelSpinner = flowLevelListInclude.findViewById<Spinner>(R.id.spinner)
        flowLevelHeading.text = getString(R.string.flow_level)
        val entries = ImageData.flowLevel
        val entryType = EntryType.DropDown
        flowLevelEntryAdapter = EntryAdapter(view.context, entries, entryType)
        flowLevelSpinner.adapter = flowLevelEntryAdapter
        flowLevelSpinner.onItemSelectedListener = SpinnerListener(flowLevelEntryAdapter)
        flowLevelSpinner.setSelection(flowLevel)
    }

    fun initializeSmallClotsSlider(view: View, smallClots: Int) {
        val smallClotsSliderInclude = view.findViewById<LinearLayout>(R.id.small_clots_slider)
        val smallClotsHeading = smallClotsSliderInclude.findViewById<TextView>(R.id.slider_heading)
        val smallClotsImage = smallClotsSliderInclude.findViewById<ImageView>(R.id.slider_image)
        smallClotsHeading.text = getString(R.string.small_clots)
        smallClotsImage.setImageResource(R.drawable.small_clots)
        smallClotsSlider = smallClotsSliderInclude.findViewById(R.id.slider)
        smallClotsSlider.valueFrom = 0.0f
        smallClotsSlider.valueTo = 10.0f
        smallClotsSlider.stepSize = 1.0f
        smallClotsSlider.value = smallClots.toFloat()
    }

    fun initializeBigClotsSlider(view: View, bigClots: Int) {
        val bigClotsSliderInclude = view.findViewById<LinearLayout>(R.id.big_clots_slider)
        val bigClotsHeading = bigClotsSliderInclude.findViewById<TextView>(R.id.slider_heading)
        val bigClotsImage = bigClotsSliderInclude.findViewById<ImageView>(R.id.slider_image)
        bigClotsHeading.text = getString(R.string.big_clots)
        bigClotsImage.setImageResource(R.drawable.big_clots)
        bigClotsSlider = bigClotsSliderInclude.findViewById(R.id.slider)
        bigClotsSlider.valueFrom = 0.0f
        bigClotsSlider.valueTo = 10.0f
        bigClotsSlider.stepSize = 1.0f
        bigClotsSlider.value = bigClots.toFloat()
    }

    fun initializeExcludeCheckBox(view: View, exclude: Boolean) {
        val excludeCheckboxInclude = view.findViewById<LinearLayout>(R.id.exclude_checkbox)
        val excludeLabel = excludeCheckboxInclude.findViewById<TextView>(R.id.checkbox_label)
        excludeLabel.text = getString(R.string.exclude)
        excludeLabel.setOnClickListener {
            excludeCheckBox.isChecked = !excludeCheckBox.isChecked
        }
        excludeCheckBox = excludeCheckboxInclude.findViewById(R.id.checkbox)
        excludeCheckBox.isChecked = exclude
    }

    fun initializeNotesEditText(view: View, notes: String) {
        val notesInclude = view.findViewById<LinearLayout>(R.id.notes_include)
        notesEditText = notesInclude.findViewById(R.id.notes)
        notesEditText.setText(notes)
    }

    override fun save() {
        val dayData = viewModel.getDayData()
        dayData.bleedingFlowLevel = flowLevelEntryAdapter.value
        dayData.bleedingSmallClots = smallClotsSlider.value.toInt()
        dayData.bleedingBigClots = bigClotsSlider.value.toInt()
        dayData.bleedingExclude = excludeCheckBox.isChecked
        dayData.bleedingNotes = notesEditText.text.toString()
        super.save()
    }

    companion object {
        const val FLOW_LEVEL = "Flow Level"
        const val SMALL_CLOTS = "Small Clots"
        const val BIG_CLOTS = "Big Clots"
        const val EXCLUDE = "Exclude"
        const val NOTES = "Notes"
    }
}
