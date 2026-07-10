package com.myperioddataismine

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

class EntryAdapter private constructor(
    context: Context,
    arrayList: ArrayList<Entry>,
    private val entryValue: EntryValue
) : ArrayAdapter<EntryAdapter.Entry>(context, entryValue.entryLayoutId, arrayList) {

    class Entry(val textId: Int, val imageId: Int)
    var value: Int
        get() {
            return entryValue.value
        }
        set(value) {
            entryValue.value = value
            notifyDataSetChanged()
        }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getEntryView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getEntryView(position, convertView, parent)
    }

    private fun getEntryView(position: Int, convertView: View?, parent: ViewGroup): View {
        val entryView: View = convertView ?: entryValue.inflateEntryView(parent)
        if (entryView.tag != position) {
            val entry = getItem(position) as Entry
            entryValue.initializeEntryView(
                entryView,
                position,
                entry.textId,
                entry.imageId
            )
        }
        entryValue.updateEntryView(entryView)
        return entryView
    }

    fun setValueFromPosition(position: Int) {
        entryValue.setValueFromPosition(position)
        notifyDataSetChanged()
    }

    companion object {
        operator fun invoke(
            context: Context,
            entries: Array<Pair<Int, Int>>,
            entryType: EntryValue.EntryType
        ): EntryAdapter {
            val arrayList = ArrayList<Entry>()
            for (entry in entries) {
                arrayList.add(Entry(entry.first, entry.second))
            }
            val entryValue = EntryValue(entryType)
            return EntryAdapter(context, arrayList, entryValue)
        }
    }
}
