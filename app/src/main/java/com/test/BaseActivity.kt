package com.test

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

open class BaseActivity : AppCompatActivity() {

    fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun addFragmentToActivity(fragment: Fragment, fragTag: String) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment, fragTag)
        if (fragTag.isNotEmpty())
            transaction.addToBackStack(fragTag)
        transaction.commit()
    }

    fun removeFragmentFromStack(fragTag: String) {
        val manager = supportFragmentManager
        manager.popBackStack()
    }

    /**
     * handling back button.
     */
    override fun onBackPressed() {
        val fm = supportFragmentManager.beginTransaction()
        if (!fm.isEmpty)
            supportFragmentManager.popBackStack()
        else {
            super.onBackPressed()
        }
    }
}