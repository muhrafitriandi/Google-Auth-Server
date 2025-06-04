package com.yandey.routes

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.yandey.domain.model.ApiRequest
import com.yandey.domain.model.Endpoint
import com.yandey.domain.model.User
import com.yandey.domain.model.UserSession
import com.yandey.domain.repository.UserDataSource
import com.yandey.util.Constants.WEB_CLIENT_ID
import com.yandey.util.Constants.ISSUER
import io.ktor.server.application.Application
import io.ktor.server.application.log
import io.ktor.server.request.receive
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Route
import io.ktor.server.routing.RoutingContext
import io.ktor.server.routing.post
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set

fun Route.tokenVerificationRoute(
    app: Application,
    userDataSource: UserDataSource,
) {
    post(Endpoint.TokenVerification.path) {
        val request = call.receive<ApiRequest>()
        if (request.tokenId.isNotEmpty()) {
            val result = verifyGoogleTokenId(tokenId = request.tokenId)
            if (result != null) {
                saveUserToDatabase(app = app, result = result, userDataSource = userDataSource)
            } else {
                app.log.info("TOKEN VERIFICATION FAILED")
                call.respondRedirect(Endpoint.Unauthorized.path)
            }
        } else {
            app.log.info("EMPTY TOKEN ID")
            call.respondRedirect(Endpoint.Unauthorized.path)
        }
    }
}

fun verifyGoogleTokenId(tokenId: String): GoogleIdToken? {
    return try {
        val verifier = GoogleIdTokenVerifier.Builder(NetHttpTransport(), GsonFactory())
            .setAudience(listOf(WEB_CLIENT_ID))
            .setIssuer(ISSUER)
            .build()
        verifier.verify(tokenId)
    } catch (e: Exception) {
        null
    }
}

private suspend fun RoutingContext.saveUserToDatabase(
    app: Application,
    result: GoogleIdToken,
    userDataSource: UserDataSource
) {
    val sub = result.payload["sub"].toString()
    val name = result.payload["name"].toString()
    val emailAddress = result.payload["email"].toString()
    val profilePhoto = result.payload["picture"].toString()
    val user = User(
        id = sub,
        name = name,
        email = emailAddress,
        profilePhoto = profilePhoto
    )

    val response = userDataSource.saveUserInfo(user = user)
    if (response) {
        app.log.info("USER SUCCESSFULLY SAVED/RETRIEVED")
        call.sessions.set(UserSession(id = sub, name = name))
        call.respondRedirect(Endpoint.Authorized.path)
    } else {
        app.log.info("ERROR SAVING THE USER")
        call.respondRedirect(Endpoint.Unauthorized.path)
    }
}