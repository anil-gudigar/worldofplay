package com.worldofplay.app

import com.facebook.stetho.Stetho
import com.worldofplay.app.di.AppInjector
import com.worldofplay.core.BaseApp
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class WorldOfPlayApp : BaseApp(), HasAndroidInjector  {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)

        initializeStetho()
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    private fun initializeStetho() {
        //Stetho helps debug network calls and database / preference values
        if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this)
    }


}