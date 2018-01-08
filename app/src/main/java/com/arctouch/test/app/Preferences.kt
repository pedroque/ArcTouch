package com.arctouch.test.app

import android.content.SharedPreferences

import javax.inject.Inject

class Preferences @Inject
constructor(private val prefs: SharedPreferences) {

    companion object {
        @JvmField
        val API_KEY = "api_key"
    }

    var api_key: String?
        get() = prefs.getString(API_KEY, "1f54bd990f1cdfb230adb312546d765d")
        set(apiKey) = prefs.edit().putString(API_KEY, apiKey).apply()
}
