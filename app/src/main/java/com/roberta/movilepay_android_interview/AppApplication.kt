package com.roberta.movilepay_android_interview

import android.app.Application
import com.roberta.movilepay_android_interview.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AppApplication)
            androidLogger(Level.ERROR)
            modules(appModules)
        }
    }
}

