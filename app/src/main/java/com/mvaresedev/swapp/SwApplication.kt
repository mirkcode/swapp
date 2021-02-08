package com.mvaresedev.swapp

import android.app.Application
import com.mvaresedev.swapp.di.appModule
import com.mvaresedev.swapp.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SwApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@SwApplication)
            modules(listOf(appModule, uiModule))
        }
    }
}