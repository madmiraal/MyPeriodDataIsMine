package com.myperioddataismine.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.myperioddataismine.MainActivity
import com.myperioddataismine.R

class WelcomeFragment : Fragment(R.layout.welcome_fragment) {
    lateinit var nameEditText: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nameEditText = view.findViewById<EditText?>(R.id.name_edit_text)
        view.findViewById<Button?>(R.id.ok_button).setOnClickListener {
            submit()
        }
    }

    private fun submit() {
        (context as MainActivity).welcomeSetName(nameEditText.text.toString())
    }
}
