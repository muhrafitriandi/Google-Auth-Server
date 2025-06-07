package com.yandey.routes

import com.yandey.domain.model.ApiResponse
import com.yandey.domain.model.Endpoint
import com.yandey.domain.model.UserSession
import com.yandey.domain.repository.UserDataSource
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.log
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.getUserInfoRoute(
    app: Application,
    userDataSource: UserDataSource,
) {
    authenticate("auth-session") {
        get(Endpoint.GetUser.path) {
            val userSession = call.principal<UserSession>()
            if (userSession == null) {
                app.log.info("INVALID SESSION")
                call.respondRedirect(Endpoint.Unauthorized.path)
            } else {
                try {
                    call.respond(
                        message = ApiResponse(
                            isSuccess = true,
                            user = userDataSource.getUserInfo(userId = userSession.id)
                        ),
                        status = HttpStatusCode.OK,
                    )
                } catch (e: Exception) {
                    app.log.info("ERROR GETTING USER INFO ${e.message}")
                    call.respondRedirect(Endpoint.Unauthorized.path)
                }
            }
        }
    }
}