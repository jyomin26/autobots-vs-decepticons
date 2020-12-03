package com.android.transformer

import android.app.Application
import com.androidnetworking.AndroidNetworking

class TransformerApplication : Application() {
    companion object {
        lateinit var instance: TransformerApplication private set
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        AndroidNetworking.initialize(getApplicationContext())
    }
}