package io.github.christfollow3r.weatherstation

import kotlinx.serialization.Serializable

enum class TemperatureStatus {
    FALLING,
    STABLE,
    RISING,
}

// TODO: Store each reading as a sensor type

@Serializable
data class Report  (
    val temperature: Float,
    val humidity: Float,
    val timeStamp: Long = System.currentTimeMillis()
)

data class DailyInfo (
    val lastUpdated: Long,
    val min: Float,
    val max: Float
)

class WeatherStation(private val maxReadings: Int = 20) {

    private val recent = ArrayDeque<Report>()

    @Synchronized
    fun addReport(report : Report) {
        recent.addLast(report)
        if (recent.size > maxReadings) recent.removeFirst()
    }

    @Synchronized
    fun averageTemperature(): Float? =
        if (recent.isEmpty()) null else recent.map { it.temperature }.average().toFloat()

    @Synchronized
    fun averageHumidity(): Float? =
        if (recent.isEmpty()) null else recent.map { it.humidity }.average().toFloat()

    @Synchronized
    fun getReport(): Report? {

        if (recent.size < maxReadings) return null

        val t = averageTemperature() ?: return null
        val h = averageHumidity() ?: return null

        return Report(t, h)

    }


}