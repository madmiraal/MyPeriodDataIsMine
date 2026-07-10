package com.myperioddataismine

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView

class EntryValue(val entryType: EntryType, var value: Int = 0) {
    enum class EntryType { DropDown, SingleEntry, MultipleEntry }

    val entryLayoutId = when (entryType) {
        EntryType.DropDown -> R.layout.list_drop_down
        EntryType.SingleEntry -> R.layout.list_single_entry
        EntryType.MultipleEntry -> R.layout.list_multiple_entry
    }

    fun createEntryView(parent: ViewGroup, position: Int, entryTextId: Int, entryImageId: Int): View {
        val entryView = inflateEntryView(parent)
        initializeEntryView(entryView, position, entryTextId, entryImageId)
        return entryView
    }

    fun inflateEntryView(parent: ViewGroup): View {
        val inflater = parent.context.getSystemService(
            Context.LAYOUT_INFLATER_SERVICE
        ) as LayoutInflater
        val entryView = inflater.inflate(
            entryLayoutId,
            parent,
            false
        )
        entryView.tag = -1
        return entryView
    }

    fun initializeEntryView(
        entryView: View,
        position: Int,
        entryTextId: Int,
        entryImageId: Int
    ) {
        entryView.tag = position
        val entryText = entryView.findViewById<TextView>(R.id.entry_text)
        val entryImage = entryView.findViewById<ImageView>(R.id.entry_image)
        val description = entryView.context.getString(entryTextId)
        entryText.text = description
        entryImage.setImageResource(entryImageId)
        entryImage.setContentDescription(description)
    }

    fun updateEntryView(entryView: View) {
        val position = entryView.tag as Int
        when (entryType) {
            EntryType.DropDown -> {}
            EntryType.SingleEntry -> {
                val entryRadioButton = entryView.findViewById<RadioButton>(R.id.entry_radio_button)
                entryRadioButton.isChecked = (position == value)
            }

            EntryType.MultipleEntry -> {
                val entryCheckBox = entryView.findViewById<CheckBox>(R.id.entry_check_box)
                val mask = 1 shl position
                entryCheckBox.isChecked = (value and mask) != 0
            }
        }
    }

    fun onEntryViewClicked(entryView: View) {
        val position = entryView.tag as Int
        setValueFromPosition(position)
        when (entryType) {
            EntryType.DropDown, EntryType.SingleEntry -> {
                val parent = entryView.parent as ViewGroup
                parent.invalidate()
            }
            EntryType.MultipleEntry -> {
                updateEntryView(entryView)
            }
        }
    }

    fun setValueFromPosition(position: Int) {
        when (entryType) {
            EntryType.DropDown, EntryType.SingleEntry -> {
                value = position
            }
            EntryType.MultipleEntry -> {
                val mask = 1 shl position
                value = value xor mask
            }
        }
    }
}
