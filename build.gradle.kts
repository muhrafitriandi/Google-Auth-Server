
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.plugin.serialization)
}

group = "com.yandey"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

tasks {
    create("stage").dependsOn("installDist")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)

    // Call Logging
    implementation(libs.ktor.server.call.logging)

    // Content Negotiation
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.content.negotiation)

    // Sessions
    implementation(libs.ktor.server.sessions)

    // Authentication
    implementation(libs.ktor.server.auth)

    // KMongo
    implementation(libs.kmongo.async)
    implementation(libs.kmongo.coroutine.serialization)

    // Koin
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)

    // Google Client API Library
    implementation(libs.google.api.client)
}
