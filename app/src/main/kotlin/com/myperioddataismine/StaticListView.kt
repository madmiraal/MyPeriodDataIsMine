package com.myperioddataismine

import android.view.View
import android.widget.LinearLayout
import androidx.core.view.iterator

class StaticListView(
    private val layout: LinearLayout,
    entries: Array<Pair<Int, Int>>,
    entryType: EntryValue.EntryType
) {
    private val entryValue = EntryValue(entryType)
    var value: Int
        get() {
            return entryValue.value
        }
        set(value) {
            entryValue.value = value
            updateView()
        }

    init {
        var position = 0
        for (entry in entries) {
            val entryTextId = entry.first
            val entryImageId = entry.second
            val entryView = entryValue.createEntryView(
                layout,
                position++,
                entryTextId,
                entryImageId
            )
            entryView.setOnClickListener { entryView: View ->
                entryValue.onEntryViewClicked(entryView)
            }
            layout.addView(entryView)
        }
    }

    fun updateView() {
        for (entryView in layout) {
            entryValue.updateEntryView(entryView)
        }
    }
}
