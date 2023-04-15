package com.example.film_app_2.application

import android.app.Application
import android.content.Context
import com.example.film_app_2.components.ActivityComponent
import com.example.film_app_2.components.DaggerActivityComponent

class Application : Application() {
    val activityComponent: ActivityComponent = DaggerActivityComponent.create()

    init {
        instance = this
    }

    companion object {
        lateinit var instance: com.example.film_app_2.application.Application

        fun getContext(): Context {
            return instance
        }
    }
}