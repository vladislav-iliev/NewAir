package com.vladislaviliev.newair

import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

class HiltTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?) =
        super.newApplication(cl, HiltTestApplication::class.java.name, context)
}