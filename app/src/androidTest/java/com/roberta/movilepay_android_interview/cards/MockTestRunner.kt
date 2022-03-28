package com.roberta.movilepay_android_interview.cards

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class MockTestRunner : AndroidJUnitRunner() {

    override fun  newApplication (cl: ClassLoader ?, className: String ?,
                                  context: Context?) : Application {
        return  super .newApplication(cl, CardsTestApp:: class .java.name, context)
    }
}