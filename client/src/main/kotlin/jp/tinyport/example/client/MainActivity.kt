package jp.tinyport.example.client

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    private var conn: ServiceConnection? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(Modifier.fillMaxSize()) {
                    Row {
                        Button(
                            onClick = {
                                log.info("onClick")

                                if (conn != null) {
                                    log.info("conn != null")
                                    return@Button
                                }

                                val resolvedInfo =
                                    packageManager.resolveService(
                                        Intent("jp.tinyport.example.server.action.MAIN"),
                                        PackageManager.MATCH_ALL
                                    ) ?: run {
                                        log.info("resolvedInfo == null")
                                        // bindService() returns false if the `<query>` not defined.
                                        return@Button
                                    }

                                log.info("resolved: %s", resolvedInfo)

                                val ret = bindService(
                                    Intent().setComponent(
                                        ComponentName(
                                            resolvedInfo.serviceInfo.packageName,
                                            resolvedInfo.serviceInfo.name
                                        )
                                        // for resolveInfo == null.
                                        // ComponentName(
                                        //     "jp.tinyport.example.server",
                                        //    "jp.tinyport.example.server.MainService",
                                        // )
                                    ),
                                    object : ServiceConnection {
                                        override fun onServiceConnected(
                                            name: ComponentName?,
                                            service: IBinder?,
                                        ) {
                                            log.info("onServiceConnected")
                                        }

                                        override fun onServiceDisconnected(name: ComponentName?) {
                                            log.info("onServiceDisconnected")
                                        }
                                    },
                                    Context.BIND_AUTO_CREATE,
                                )

                                log.info("bindService: %s", ret)
                            }
                        ) {
                            Text("Connect")
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        log.info("onDestroy")

        conn?.let {
            unbindService(it)
            this.conn = null
        }

        super.onDestroy()
    }
}
