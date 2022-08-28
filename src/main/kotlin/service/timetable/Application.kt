package service.timetable

import kotlinx.coroutines.runBlocking
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main() = runBlocking {

    val departureDisplay = DepartureDisplay(TimetableService(), RealTimeService())

    while (true) {
        val time = measureTime {
            departureDisplay.update()
        }
        println("(update took $time")
       Thread.sleep(5000L)
    }
}
