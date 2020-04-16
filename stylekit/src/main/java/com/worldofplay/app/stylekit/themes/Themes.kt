package com.worldofplay.app.stylekit.themes

import com.worldofplay.app.stylekit.R

class Themes {
    companion object {
        const val DEFAULT = 0
        const val DARK_BLUE = 1
        const val LIGHT_BLUE = 2

        fun getThemeId(theme: Int): Int {
            var themeId: Int = R.style.AppTheme
            when (theme) {
                DARK_BLUE -> {
                    themeId = R.style.AppTheme_DARK_BLUE
                }
                LIGHT_BLUE -> {
                    themeId = R.style.AppTheme_LIGHT_BLUE
                }
                DEFAULT -> {
                    themeId = R.style.AppTheme
                }
            }
            return themeId
        }
    }
}