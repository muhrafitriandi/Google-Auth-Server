package com.yandey

import com.yandey.plugins.configureAuthentication
import com.yandey.plugins.configureDatabases
import com.yandey.plugins.configureFrameworks
import com.yandey.plugins.configureMonitoring
import com.yandey.plugins.configureRouting
import com.yandey.plugins.configureSerialization
import com.yandey.plugins.configureSession
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureAuthentication()
    configureSerialization()
    configureDatabases()
    configureMonitoring()
    configureFrameworks()
    configureRouting()
    configureSession()
}
