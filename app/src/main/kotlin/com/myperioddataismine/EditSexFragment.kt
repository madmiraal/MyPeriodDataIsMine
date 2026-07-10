package com.myperioddataismine

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

class EditSexFragment: EditSectionFragment(R.layout.edit_sex_fragment) {
    private lateinit var activitiesList: StaticListView
    private lateinit var protectionsList: StaticListView
    private lateinit var notesEditText: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dayData = viewModel.getDayData()
        val activities = cachedData?.getInt(ACTIVITIES)
            ?: dayData.sexActivities
        val protections = cachedData?.getInt(PROTECTIONS)
            ?: dayData.sexProtections
        val notes = cachedData?.getString(NOTES)
            ?: dayData.sexNotes

        initializeActivitiesList(view, activities)
        initializeProtectionsList(view, protections)
        initializeNotesEditText(view, notes)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(ACTIVITIES, activitiesList.value)
        outState.putInt(PROTECTIONS, protectionsList.value)
        outState.putString(NOTES, notesEditText.text.toString())
    }

    fun initializeActivitiesList(view: View, activities: Int) {
        val activitiesHeading = view.findViewById<TextView>(R.id.activities_heading)
        activitiesHeading.text = getString(R.string.activities)
        val activitiesListLayout = view.findViewById<LinearLayout>(R.id.activities_list)
        val entries = ImageData.sexActivities
        val entryType = EntryValue.EntryType.MultipleEntry
        activitiesList = StaticListView(activitiesListLayout, entries, entryType)
        activitiesList.value = activities
    }

    fun initializeProtectionsList(view: View, protections: Int) {
        val protectionsHeading = view.findViewById<TextView>(R.id.protections_heading)
        protectionsHeading.text = getString(R.string.protections)
        val protectionsListLayout = view.findViewById<LinearLayout>(R.id.protections_list)
        val entries = ImageData.sexProtections
        val entryType = EntryValue.EntryType.MultipleEntry
        protectionsList = StaticListView(protectionsListLayout, entries, entryType)
        protectionsList.value = protections
    }

    fun initializeNotesEditText(view: View, notes: String) {
        val notesInclude = view.findViewById<LinearLayout>(R.id.notes_include)
        notesEditText = notesInclude.findViewById(R.id.notes)
        notesEditText.setText(notes)
    }

    override fun save() {
        val dayData = viewModel.getDayData()
        dayData.sexActivities = activitiesList.value
        dayData.sexProtections = protectionsList.value
        dayData.sexNotes = notesEditText.text.toString()
        super.save()
    }

    companion object {
        const val ACTIVITIES = "Activities"
        const val PROTECTIONS = "Protections"
        const val NOTES = "Notes"
    }
}
