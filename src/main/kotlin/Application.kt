package com.yandey

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
