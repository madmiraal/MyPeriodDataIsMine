package com.myperioddataismine

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import com.myperioddataismine.EntryValue.EntryType

class EditSymptomsFragment: EditSectionFragment(R.layout.edit_symptoms_fragment) {
    private lateinit var symptomsList: StaticListView
    private lateinit var notesEditText: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dayData = viewModel.getDayData()
        val symptoms = cachedData?.getInt(SYMPTOMS)
            ?: dayData.symptoms
        val notes = cachedData?.getString(NOTES)
            ?: dayData.symptomsNotes

        initializeSymptomsList(view, symptoms)
        initializeNotesEditText(view, notes)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SYMPTOMS, symptomsList.value)
        outState.putString(NOTES, notesEditText.text.toString())
    }

    fun initializeSymptomsList(view: View, symptoms: Int) {
        val symptomsListLayout = view.findViewById<LinearLayout>(R.id.symptoms_list)
        val entries = ImageData.symptoms
        val entryType = EntryType.MultipleEntry
        symptomsList = StaticListView(symptomsListLayout, entries, entryType)
        symptomsList.value = symptoms
    }

    fun initializeNotesEditText(view: View, notes: String) {
        val notesInclude = view.findViewById<LinearLayout>(R.id.notes_include)
        notesEditText = notesInclude.findViewById(R.id.notes)
        notesEditText.setText(notes)
    }

    override fun save() {
        val dayData = viewModel.getDayData()
        dayData.symptoms = symptomsList.value
        dayData.symptomsNotes = notesEditText.text.toString()
        super.save()
    }

    companion object {
        const val SYMPTOMS = "Symptoms"
        const val NOTES = "Notes"
    }
}
