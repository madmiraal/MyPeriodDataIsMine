package com.myperioddataismine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.myperioddataismine.ui.main.PasscodeFragment
import com.myperioddataismine.ui.main.WelcomeFragment

class MainActivity : AppCompatActivity() {
    lateinit var encryptedDatabase: EncryptedDatabase
    var name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        encryptedDatabase = EncryptedDatabase(this)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                if (encryptedDatabase.exists()) {
                    replace<PasscodeFragment>(R.id.main_container)
                } else {
                    replace<WelcomeFragment>(R.id.main_container)
                }
            }
        }
    }

    fun welcomeSetName(newName: String) {
        name = newName
        val bundle = bundleOf("create" to true)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<PasscodeFragment>(R.id.main_container, args = bundle)
        }
    }
}
