package jp.tinyport.example.server

import android.app.Application
import jp.tinyport.logger.ConsoleLogDestination
import jp.tinyport.logger.Logger

val log = Logger()

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        log.addDestination(ConsoleLogDestination("ExampleServer"))
    }
}
