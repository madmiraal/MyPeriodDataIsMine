package com.myperioddataismine

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import com.myperioddataismine.EntryValue.EntryType

class EditPainsFragment: EditSectionFragment(R.layout.edit_pains_fragment) {
    private lateinit var painsList: StaticListView
    private lateinit var notesEditText: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dayData = viewModel.getDayData()
        val pains = cachedData?.getInt(PAINS)
            ?: dayData.pains
        val notes = cachedData?.getString(NOTES)
            ?: dayData.painsNotes

        initializePainsList(view, pains)
        initializeNotesEditText(view, notes)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(PAINS, painsList.value)
        outState.putString(NOTES, notesEditText.text.toString())
    }

    fun initializePainsList(view: View, pains: Int) {
        val painsListLayout = view.findViewById<LinearLayout>(R.id.pains_list)
        val entries = ImageData.pains
        val entryType = EntryType.MultipleEntry
        painsList = StaticListView(painsListLayout, entries, entryType)
        painsList.value = pains
    }

    fun initializeNotesEditText(view: View, notes: String) {
        val notesInclude = view.findViewById<LinearLayout>(R.id.notes_include)
        notesEditText = notesInclude.findViewById(R.id.notes)
        notesEditText.setText(notes)
    }

    override fun save() {
        val dayData = viewModel.getDayData()
        dayData.pains = painsList.value
        dayData.painsNotes = notesEditText.text.toString()
        super.save()
    }

    companion object {
        const val PAINS = "Pains"
        const val NOTES = "Notes"
    }
}
