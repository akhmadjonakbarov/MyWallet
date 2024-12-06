package com.akbarovdev.mywallet.core.database

import android.content.Context
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SharedPreferencesDelegate(
    private val context: Context,
    private val key: String,
    private val defaultValue: String = ""
) : ReadWriteProperty<Any?, String> {

    private val sharedPreferences by lazy {
        context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE) // Fixed: Using shared preference name separately
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue // Corrected: Use key for the shared pref key
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        sharedPreferences.edit().putString(key, value).apply() // Corrected: Use key for the shared pref key
    }
}


fun Context.sharedPreferences(name: String, defaultValue: String = "") =
    SharedPreferencesDelegate(this, name, defaultValue)