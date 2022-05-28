package jp.tinyport.example.server

import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.lifecycle.LifecycleService

class MainService : LifecycleService() {
    override fun onCreate() {
        super.onCreate()

        log.info("onCreate")
    }

    override fun onDestroy() {
        log.info("onDestroy")

        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        log.info("onBind")
        return object : Binder() {}
    }

    override fun onUnbind(intent: Intent?): Boolean {
        super.onUnbind(intent)
        log.info("onUnbind")
        return false
    }
}
