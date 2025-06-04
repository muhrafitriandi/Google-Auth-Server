package com.yandey.plugins

import com.yandey.routes.authorizedRoute
import com.yandey.routes.rootRoute
import com.yandey.routes.tokenVerificationRoute
import com.yandey.routes.unauthorizedRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        rootRoute()
        tokenVerificationRoute(application)
        authorizedRoute()
        unauthorizedRoute()
    }
}
