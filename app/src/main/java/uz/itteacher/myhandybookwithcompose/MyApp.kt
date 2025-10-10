package uz.itteacher.myhandybookwithcompose

import android.app.Application
import timber.log.Timber

class MyApp : Application() {
    companion object {
        lateinit var instance: MyApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Timber.plant(Timber.DebugTree())
    }
}