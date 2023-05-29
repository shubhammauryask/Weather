package com.example.weather

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceManager (context: Context) {
    private val CITY_NAME = "city"
    private val sharedPreferences : SharedPreferences = context.getSharedPreferences("Whether", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun saveData(key : String, value : String){
        editor.putString(key, value)
        editor.apply()
    }

    fun getData(key: String) : String? {
        return sharedPreferences.getString(key, "Delhi")
    }
}