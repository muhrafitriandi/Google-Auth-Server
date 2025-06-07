package com.yandey.routes

import com.yandey.domain.model.ApiResponse
import com.yandey.domain.model.Endpoint
import com.yandey.domain.model.UserSession
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.sessions.clear
import io.ktor.server.sessions.sessions

fun Route.signOutRoute() {
    authenticate("auth-session") {
        get(Endpoint.SignOut.path) {
            call.sessions.clear<UserSession>()
            call.respond(
                message = ApiResponse(isSuccess = true, message = "Successfully sign out!"),
                status = HttpStatusCode.OK
            )
        }
    }
}