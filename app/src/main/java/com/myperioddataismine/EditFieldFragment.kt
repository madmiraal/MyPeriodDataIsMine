package com.myperioddataismine

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.myperioddataismine.EntryAdapter.EntryType

class EditFieldFragment(
    private val field: DayData.Field,
    private val currentValue: Int
) : Fragment(R.layout.edit_field_fragment) {
    private lateinit var entryAdapter: EntryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editFieldHeading = view.findViewById<TextView>(R.id.edit_field_heading)
        val listView = view.findViewById<ListView>(R.id.list_view)
        val buttonsLayout = view.findViewById<LinearLayout>(R.id.save_cancel_buttons)
        val saveButton = buttonsLayout.findViewById<Button>(R.id.save_button)
        val cancelButton = buttonsLayout.findViewById<Button>(R.id.cancel_button)

        editFieldHeading.text = editFieldHeadingForField(field)
        val entries = entriesForField(field)
        val entryType = entryTypeForField(field)

        entryAdapter = EntryAdapter(view.context, entries, entryType)
        listView.adapter = entryAdapter
        listView.setOnItemClickListener { _: AdapterView<*>, _: View, position: Int, _: Long ->
            entryAdapter.updateValue(position)
        }
        entryAdapter.value = currentValue

        saveButton.setOnClickListener { save() }
        cancelButton.setOnClickListener { cancel() }
    }

    private fun editFieldHeadingForField(field: DayData.Field): String {
        return when (field) {
            DayData.Field.FlowLevel -> getString(R.string.select_flow_level)
            DayData.Field.Moods -> getString(R.string.select_moods)
            DayData.Field.Symptoms -> getString(R.string.select_symptoms)
        }
    }

    private fun entriesForField(field: DayData.Field): Array<Pair<Int,Int>> {
        return when (field) {
            DayData.Field.FlowLevel -> DayData.flowLevelValues
            DayData.Field.Moods -> DayData.moodValues
            DayData.Field.Symptoms -> DayData.symptomValues
        }
    }

    private fun entryTypeForField(field: DayData.Field): EntryType {
        return when (field) {
            DayData.Field.FlowLevel -> EntryType.SingleEntry
            DayData.Field.Moods -> EntryType.MultipleEntry
            DayData.Field.Symptoms -> EntryType.MultipleEntry
        }
    }

    private fun save() {
        (context as MainActivity).saveField(field, entryAdapter.value)
    }

    private fun cancel() {
        (context as MainActivity).back()
    }
}
