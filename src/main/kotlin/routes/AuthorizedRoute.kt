package com.yandey.routes

import com.yandey.domain.model.ApiResponse
import com.yandey.domain.model.Endpoint
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.authorizedRoute() {
    authenticate("auth-session") {
        get(Endpoint.Authorized.path) {
            call.respond(
                message = ApiResponse(isSuccess = true),
                status = HttpStatusCode.OK
            )
        }
    }
}