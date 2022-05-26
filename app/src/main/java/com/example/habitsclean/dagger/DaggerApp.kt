package com.example.habitsclean.dagger

import android.app.Application

class DaggerApp : Application() {

    private var _applicationComponent: ApplicationComponent? = null

    val applicationComponent: ApplicationComponent
        get() = checkNotNull(_applicationComponent)

    override fun onCreate() {
        super.onCreate()
        _applicationComponent = DaggerApplicationComponent.builder()
            .contextModule(ContextModule(this)).build()
    }
}