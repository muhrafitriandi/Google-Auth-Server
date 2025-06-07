package com.yandey.plugins

import com.yandey.domain.repository.UserDataSource
import com.yandey.routes.authorizedRoute
import com.yandey.routes.deleteUserRoute
import com.yandey.routes.getUserInfoRoute
import com.yandey.routes.rootRoute
import com.yandey.routes.signOutRoute
import com.yandey.routes.tokenVerificationRoute
import com.yandey.routes.unauthorizedRoute
import com.yandey.routes.updateUserRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Application.configureRouting() {
    routing {
        val userDataSource: UserDataSource by inject(UserDataSource::class.java)

        rootRoute()
        tokenVerificationRoute(application, userDataSource)
        getUserInfoRoute(application, userDataSource)
        updateUserRoute(application, userDataSource)
        deleteUserRoute(application, userDataSource)
        signOutRoute()
        authorizedRoute()
        unauthorizedRoute()
    }
}
