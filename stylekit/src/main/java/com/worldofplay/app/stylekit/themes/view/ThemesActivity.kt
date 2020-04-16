package com.worldofplay.app.stylekit.themes.view

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.worldofplay.app.stylekit.themes.Themes

abstract class ThemesActivity : AppCompatActivity() {
    var mTheme: Int = Themes.DEFAULT
    var mIsNightMode = false
    abstract val layout: Int
   lateinit var mPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        mIsNightMode = mPreferences.getBoolean("nightMode", false)
        mTheme = mPreferences.getInt("themeId", Themes.DEFAULT)
        setTheme(Themes.getThemeId(mTheme))
        setContentView(layout)
        //extractNightMode()
        initUI()
    }

    private fun extractNightMode() {
        window.decorView.rootView.postDelayed(Runnable {
            if (mIsNightMode) {
                delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES
            } else {
                delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO
            }
        }, 400)
    }

    abstract fun initUI()
}