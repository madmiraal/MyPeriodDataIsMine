package com.myperioddataismine

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

open class EditSectionFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {
    protected val viewModel: MainViewModel by activityViewModels()
    protected var cachedData: Bundle? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cachedData = getCachedData(savedInstanceState)
        initializeButtons(view)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(DATE, PeriodDataTable.intFromCalendar(viewModel.getDayData().date))
    }

    protected fun getCachedData(savedInstanceState: Bundle?): Bundle? {
        // Only use savedInstanceState data if the date hasn't changed.
        return if (
            savedInstanceState != null &&
            savedInstanceState.getInt(DATE) == PeriodDataTable.intFromCalendar(viewModel.getDayData().date)
        ) {
            savedInstanceState
        } else {
            null
        }
    }

    fun initializeButtons(view: View) {
        val saveCancelButtonsInclude = view.findViewById<LinearLayout>(R.id.save_cancel_buttons)
        val saveButton = saveCancelButtonsInclude.findViewById<Button>(R.id.save_button)
        val cancelButton = saveCancelButtonsInclude.findViewById<Button>(R.id.cancel_button)
        saveButton.setOnClickListener { save() }
        cancelButton.setOnClickListener { cancel() }
    }

    protected open fun save() {
        viewModel.saveDayData(viewModel.getDayData())
        (context as MainActivity).back()
    }

    protected fun cancel() {
        (context as MainActivity).back()
    }

    companion object {
        const val DATE = "Date"
    }

    class SpinnerListener(val entryAdapter: EntryAdapter) :
        AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            adapterView: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            entryAdapter.setValueFromPosition(position)
        }

        override fun onNothingSelected(adapterView: AdapterView<*>?) {}
    }
}
