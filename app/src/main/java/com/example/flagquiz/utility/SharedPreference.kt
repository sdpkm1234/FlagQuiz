package com.example.flagquiz.utility

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(val context: Context) {
    private val PREFS_NAME = "MyPrefsFile"
    val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun putString(KEY_NAME: String, text: String) {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.putString(KEY_NAME, text)

        editor.apply()
    }



    fun getString(KEY_NAME: String): String? {

        return sharedPref.getString(KEY_NAME, null)


    }


    fun putBoolean(KEY_NAME: String, value: Boolean) {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.putBoolean(KEY_NAME, value)

        editor.apply()
    }



    fun getBoolean(KEY_NAME: String): Boolean {

        return sharedPref.getBoolean(KEY_NAME, false)


    }


    fun putInt(KEY_NAME: String, text: Int) {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.putInt(KEY_NAME, text)

        editor.apply()
    }



    fun getInt(KEY_NAME: String): Int {

        return sharedPref.getInt(KEY_NAME, 0)


    }


    fun putLong(KEY_NAME: String, text: Long) {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.putLong(KEY_NAME, text)

        editor.apply()
    }



    fun getLong(KEY_NAME: String): Long {

        return sharedPref.getLong(KEY_NAME, 0)


    }




}