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
import io.ktor.server.routing.RoutingContext
import io.ktor.server.routing.delete
import io.ktor.server.sessions.clear
import io.ktor.server.sessions.sessions

fun Route.deleteUserRoute(
    app: Application,
    userDataSource: UserDataSource,
) {
    authenticate("auth-session") {
        delete(Endpoint.DeleteUser.path) {
            val userSession = call.principal<UserSession>()
            if (userSession == null) {
                app.log.info("INVALID SESSION")
                call.respondRedirect(Endpoint.Unauthorized.path)
            } else {
                try {
                    call.sessions.clear<UserSession>()
                    deleteUser(
                        app = app,
                        userId = userSession.id,
                        userDataSource = userDataSource
                    )
                } catch (e: Exception) {
                    app.log.info("DELETING USER ERROR: ${e.message}")
                    call.respondRedirect(Endpoint.Unauthorized.path)
                }
            }
        }
    }
}

suspend fun RoutingContext.deleteUser(
    app: Application,
    userId: String,
    userDataSource: UserDataSource
) {
    val response = userDataSource.deleteUser(userId = userId)
    if (response) {
        app.log.info("USER SUCCESSFULLY DELETED")
        call.respond(
            message = ApiResponse(
                isSuccess = true,
                message = "Successfully Deleted!"
            ),
            status = HttpStatusCode.OK
        )
    } else {
        app.log.info("ERROR DELETING THE USER")
        call.respond(
            message = ApiResponse(isSuccess = false),
            status = HttpStatusCode.BadRequest
        )
    }
}