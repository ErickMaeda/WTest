package pt.wtest

import android.app.Application

class MainApplication : Application() {

    companion object {
        var application: Application? = null
    }

    override fun onCreate() {
        super.onCreate()

        application = this
    }
}