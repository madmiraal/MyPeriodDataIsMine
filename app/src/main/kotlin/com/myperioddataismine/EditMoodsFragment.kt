package com.myperioddataismine

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import com.myperioddataismine.EntryValue.EntryType

class EditMoodsFragment: EditSectionFragment(R.layout.edit_moods_fragment) {
    private lateinit var moodsList: StaticListView
    private lateinit var notesEditText: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dayData = viewModel.getDayData()
        val moods = cachedData?.getInt(MOODS)
            ?: dayData.moods
        val notes = cachedData?.getString(NOTES)
            ?: dayData.moodsNotes

        initializeMoodsList(view, moods)
        initializeNotesEditText(view, notes)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(MOODS, moodsList.value)
        outState.putString(NOTES, notesEditText.text.toString())
    }

    fun initializeMoodsList(view: View, moods: Int) {
        val moodsListLayout = view.findViewById<LinearLayout>(R.id.moods_list)
        val entries = ImageData.moods
        val entryType = EntryType.MultipleEntry
        moodsList = StaticListView(moodsListLayout, entries, entryType)
        moodsList.value = moods
    }

    fun initializeNotesEditText(view: View, notes: String) {
        val notesInclude = view.findViewById<LinearLayout>(R.id.notes_include)
        notesEditText = notesInclude.findViewById(R.id.notes)
        notesEditText.setText(notes)
    }

    override fun save() {
        val dayData = viewModel.getDayData()
        dayData.moods = moodsList.value
        dayData.moodsNotes = notesEditText.text.toString()
        super.save()
    }

    companion object {
        const val MOODS = "Moods"
        const val NOTES = "Notes"
    }
}
