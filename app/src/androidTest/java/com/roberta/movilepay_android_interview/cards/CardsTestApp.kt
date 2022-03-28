package com.roberta.movilepay_android_interview.cards

import com.roberta.movilepay_android_interview.AppApplication
import com.roberta.movilepay_android_interview.di.URL_BASE
import com.roberta.movilepay_android_interview.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CardsTestApp : AppApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CardsTestApp)
            androidLogger()
            modules(appModules)
            properties(mapOf(URL_BASE to "http://localhost:8080"))

        }
    }

    var url = "http://localhost:8080"

    fun getBaseUrl(): String {
        return url
    }
}
