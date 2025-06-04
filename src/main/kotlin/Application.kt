package com.yandey

import com.yandey.plugins.configureAuthentication
import com.yandey.plugins.configureKoin
import com.yandey.plugins.configureMonitoring
import com.yandey.plugins.configureRouting
import com.yandey.plugins.configureSerialization
import com.yandey.plugins.configureSession
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureKoin()
    configureAuthentication()
    configureRouting()
    configureSerialization()
    configureMonitoring()
    configureSession()
}
