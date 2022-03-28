package com.roberta.movilepay_android_interview

import android.app.Application
import com.roberta.movilepay_android_interview.di.URL_BASE
import com.roberta.movilepay_android_interview.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

open class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AppApplication)
            androidLogger(Level.ERROR)
            modules(appModules)
            properties(mapOf(URL_BASE to "https://mpay-android-interview.herokuapp.com/android/interview/"))
        }
    }
}

