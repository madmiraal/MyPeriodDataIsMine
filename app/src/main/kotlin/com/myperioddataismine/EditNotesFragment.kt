package com.myperioddataismine

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout

class EditNotesFragment: EditSectionFragment(R.layout.edit_notes_fragment) {
    private lateinit var notesEditText: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dayData = viewModel.getDayData()
        val cachedData = getCachedData(savedInstanceState)
        val notes = cachedData?.getString(NOTES)
            ?: dayData.notes

        initializeNotesEditText(view, notes)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(NOTES, notesEditText.text.toString())
    }

    fun initializeNotesEditText(view: View, notes: String) {
        val notesInclude = view.findViewById<LinearLayout>(R.id.notes_include)
        notesEditText = notesInclude.findViewById(R.id.notes)
        notesEditText.setText(notes)
        notesEditText.minLines = 5
    }

    override fun save() {
        val dayData = viewModel.getDayData()
        dayData.notes = notesEditText.text.toString()
        super.save()
    }

    companion object {
        const val NOTES = "Notes"
    }
}
