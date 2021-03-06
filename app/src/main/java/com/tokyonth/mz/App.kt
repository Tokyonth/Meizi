package com.tokyonth.mz

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class App : Application() {

    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
            private set

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        if (base != null) {
            context = base
        }
    }

}
