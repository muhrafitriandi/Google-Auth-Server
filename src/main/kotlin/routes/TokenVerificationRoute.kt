package com.yandey.routes

import com.yandey.domain.model.Endpoint
import com.yandey.domain.model.UserSession
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set

fun Route.tokenVerificationRoute() {
    post(Endpoint.TokenVerification.path) {
        call.sessions.set(UserSession(id = "123", name = "Yandey"))
        call.respondRedirect(Endpoint.Authorized.path)
    }
}