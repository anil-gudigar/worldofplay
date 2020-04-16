package com.worldofplay.app.stylekit.themes.view

import android.os.Bundle
import com.worldofplay.app.stylekit.themes.Themes
import com.worldofplay.core.view.BaseActivity

open class ThemesActivity : BaseActivity() {
    var mTheme: Int = Themes.DEFAULT
    var mIsNightMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(Themes.getThemeId(mTheme))
    }
}