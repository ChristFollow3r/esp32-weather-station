package io.github.christfollow3r.weatherstation

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.http.content.*
import io.ktor.server.request.header
import io.ktor.server.request.receive
import kotlinx.serialization.SerializationException

fun Application.configureRouting() {

    val apiKey: String? = System.getenv("STATION_API_KEY")
    check(!apiKey.isNullOrEmpty()) {"STATION_API_KEY is not set"}

    val stationTest = WeatherStation(20)

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

        post("/api/report") {

            if (call.request.header("STATION-API-KEY") != apiKey) {
                call.respond(HttpStatusCode.Unauthorized)
                return@post
            }

            try {
                val report = call.receive<Report>()
                val t = report.temperature
                val h = report.humidity

                if (t < -10f || t > 55f || h < 0 || h > 100) { // Safe checks just in case crazy values come in
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }

                stationTest.addReport(report)
                call.respond(HttpStatusCode.Created)
            }

            catch (ex: IllegalStateException) { call.respond(HttpStatusCode.BadRequest) }
            catch (ex: SerializationException) { call.respond(HttpStatusCode.BadRequest) }
        }
    }
}