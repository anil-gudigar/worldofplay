package com.worldofplay.app.ui

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.worldofplay.app.R
import com.worldofplay.app.stylekit.themes.view.ThemesActivity
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class MainActivity : ThemesActivity(),HasAndroidInjector{
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    override val layout: Int = R.layout.activity_main

    override fun initUI() {
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_topStories))
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun androidInjector() = dispatchingAndroidInjector
}
