package com.yandey.routes

import com.yandey.domain.model.Endpoint
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get

fun Routing.rootRoute() {
    get(Endpoint.Root.path) {
        call.respondText("Welcome to Ktor Server!")
    }
}