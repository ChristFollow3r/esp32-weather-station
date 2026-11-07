package io.github.christfollow3r.weatherstation

import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.http.content.*

fun Application.configureRouting() {

    val stationTest: WeatherStation = WeatherStation(1)
    stationTest.addReport(Report(20F, 50F))

    routing {
        get("/report") {
            val report = stationTest.getReport()
            if (report == null) {
                call.respondText("Error: Not enough readings", status = HttpStatusCode.ServiceUnavailable)
                return@get
            }
            call.respond(listOf(report))
        }

        staticResources("/static", "static")

        post("/report") {

        }
    }
}