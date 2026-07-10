package com.myperioddataismine

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.slider.Slider

class EditContraceptionFragment: EditSectionFragment(R.layout.edit_contraception_fragment) {
    private lateinit var pillCheckBox: CheckBox
    private lateinit var patchesSlider: Slider
    private lateinit var iudCheckBox: CheckBox
    private lateinit var ringCheckBox: CheckBox
    private lateinit var implantCheckBox: CheckBox
    private lateinit var shotCheckBox: CheckBox
    private lateinit var notesEditText: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dayData = viewModel.getDayData()
        val cachedData = getCachedData(savedInstanceState)
        val pill = cachedData?.getBoolean(PILL)
            ?: dayData.contraceptionPill
        val patches = cachedData?.getInt(PATCHES)
            ?: dayData.contraceptionPatches
        val iud = cachedData?.getBoolean(IUD)
            ?: dayData.contraceptionPill
        val ring = cachedData?.getBoolean(RING)
            ?: dayData.contraceptionPill
        val implant = cachedData?.getBoolean(IMPLANT)
            ?: dayData.contraceptionPill
        val shot = cachedData?.getBoolean(SHOT)
            ?: dayData.contraceptionPill
        val notes = cachedData?.getString(NOTES)
            ?: dayData.contraceptionNotes

        initializePillCheckBox(view, pill)
        initializePatchesSlider(view, patches)
        initializeIUDCheckBox(view, iud)
        initializeRingCheckBox(view, ring)
        initializeImplantCheckBox(view, implant)
        initializeShotCheckBox(view, shot)
        initializeNotesEditText(view, notes)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(PILL, pillCheckBox.isChecked)
        outState.putInt(PATCHES, patchesSlider.value.toInt())
        outState.putBoolean(IUD, iudCheckBox.isChecked)
        outState.putBoolean(RING, ringCheckBox.isChecked)
        outState.putBoolean(IMPLANT, implantCheckBox.isChecked)
        outState.putBoolean(SHOT, shotCheckBox.isChecked)
        outState.putString(NOTES, notesEditText.text.toString())
    }

    fun initializePillCheckBox(view: View, pill: Boolean) {
        val pillCheckboxInclude = view.findViewById<LinearLayout>(R.id.pill_checkbox)
        val pillLabel = pillCheckboxInclude.findViewById<TextView>(R.id.checkbox_label)
        pillLabel.text = getString(R.string.pill)
        pillLabel.setOnClickListener {
            pillCheckBox.isChecked = !pillCheckBox.isChecked
        }
        pillCheckBox = pillCheckboxInclude.findViewById(R.id.checkbox)
        pillCheckBox.isChecked = pill
    }

    fun initializePatchesSlider(view: View, patches: Int) {
        val patchesSliderInclude = view.findViewById<LinearLayout>(R.id.patches_slider)
        val patchesHeading = patchesSliderInclude.findViewById<TextView>(R.id.slider_heading)
        val patchesImage = patchesSliderInclude.findViewById<ImageView>(R.id.slider_image)
        patchesHeading.text = getString(R.string.patches)
        patchesImage.setImageResource(R.drawable.patches_placeholder)
        patchesSlider = patchesSliderInclude.findViewById(R.id.slider)
        patchesSlider.valueFrom = 0.0f
        patchesSlider.valueTo = 3.0f
        patchesSlider.stepSize = 1.0f
        patchesSlider.value = patches.toFloat()
    }

    fun initializeIUDCheckBox(view: View, iud: Boolean) {
        val iudCheckboxInclude = view.findViewById<LinearLayout>(R.id.iud_checkbox)
        val iudLabel = iudCheckboxInclude.findViewById<TextView>(R.id.checkbox_label)
        iudLabel.text = getString(R.string.iud)
        iudLabel.setOnClickListener {
            iudCheckBox.isChecked = !iudCheckBox.isChecked
        }
        iudCheckBox = iudCheckboxInclude.findViewById(R.id.checkbox)
        iudCheckBox.isChecked = iud
    }

    fun initializeRingCheckBox(view: View, ring: Boolean) {
        val ringCheckboxInclude = view.findViewById<LinearLayout>(R.id.ring_checkbox)
        val ringLabel = ringCheckboxInclude.findViewById<TextView>(R.id.checkbox_label)
        ringLabel.text = getString(R.string.ring)
        ringLabel.setOnClickListener {
            ringCheckBox.isChecked = !ringCheckBox.isChecked
        }
        ringCheckBox = ringCheckboxInclude.findViewById(R.id.checkbox)
        ringCheckBox.isChecked = ring
    }

    fun initializeImplantCheckBox(view: View, implant: Boolean) {
        val implantCheckboxInclude = view.findViewById<LinearLayout>(R.id.implant_checkbox)
        val implantLabel = implantCheckboxInclude.findViewById<TextView>(R.id.checkbox_label)
        implantLabel.text = getString(R.string.implant)
        implantLabel.setOnClickListener {
            implantCheckBox.isChecked = !implantCheckBox.isChecked
        }
        implantCheckBox = implantCheckboxInclude.findViewById(R.id.checkbox)
        implantCheckBox.isChecked = implant
    }

    fun initializeShotCheckBox(view: View, shot: Boolean) {
        val shotCheckboxInclude = view.findViewById<LinearLayout>(R.id.shot_checkbox)
        val shotLabel = shotCheckboxInclude.findViewById<TextView>(R.id.checkbox_label)
        shotLabel.text = getString(R.string.shot)
        shotLabel.setOnClickListener {
            shotCheckBox.isChecked = !shotCheckBox.isChecked
        }
        shotCheckBox = shotCheckboxInclude.findViewById(R.id.checkbox)
        shotCheckBox.isChecked = shot
    }

    fun initializeNotesEditText(view: View, notes: String) {
        val notesInclude = view.findViewById<LinearLayout>(R.id.notes_include)
        notesEditText = notesInclude.findViewById(R.id.notes)
        notesEditText.setText(notes)
    }

    override fun save() {
        val dayData = viewModel.getDayData()
        dayData.contraceptionPill = pillCheckBox.isChecked
        dayData.contraceptionPatches = patchesSlider.value.toInt()
        dayData.contraceptionIUD = iudCheckBox.isChecked
        dayData.contraceptionRing = ringCheckBox.isChecked
        dayData.contraceptionImplant = implantCheckBox.isChecked
        dayData.contraceptionShot = shotCheckBox.isChecked
        dayData.contraceptionNotes = notesEditText.text.toString()
        super.save()
    }

    companion object {
        const val PILL = "Pill"
        const val PATCHES = "Patches"
        const val IUD = "IUD"
        const val RING = "Ring"
        const val IMPLANT = "Implant"
        const val SHOT = "Shot"
        const val NOTES = "Notes"
    }
}
