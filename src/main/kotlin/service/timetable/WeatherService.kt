package service.timetable

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

class WeatherService {

    fun weatherReport(destinationId: String): WeatherReport {
        val temperature = Random.nextInt(10, 20)
        val description = weatherDescriptions[Random.nextInt(0, weatherDescriptions.size)]
        return runBlocking {
            delay(500L)
            WeatherReport(description, temperature)
        }
    }

}

val weatherDescriptions = listOf(
    "Rainy", "Cloudy", "Sunny"
)
